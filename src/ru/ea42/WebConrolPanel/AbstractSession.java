package ru.ea42.WebRemoteControl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public abstract class AbstractSession {
	public String id;
	public boolean inEnterMode;
	public int lastPack;
	public Date lastTime;

	public String reqParse(JsonObject jsRoot, String sType) {
		// обновленеие компанента
		if (sType.equals("RefWidget")) {
			return absRefWidget(jsRoot);
		}

		// цикличное обновленеие экрана
		if (sType.equals("ReqQv")) {
			return quReqQv(jsRoot);
		}

		// методы из дочерних классов
		return sesReqParse(jsRoot, sType);
	}

	private static Integer NomerId = 0;

	public String GetNextId() {
		NomerId = NomerId + 1;
		return "u" + NomerId;
	}

	public ArrayList<Ui_Abstract> listForm = new ArrayList<Ui_Abstract>();
	public Ui_Abstract CurrentForm;

	public void addForm(Ui_Abstract form) {
		listForm.add(form);
	}

	public abstract String RefWidgetForm(String idWidget);

	public abstract String RefWidgetVal(String idWidget);

	public abstract String RefWidgetSetHTML(String idWidget);

	private String absRefWidget(JsonObject jsRoot) {
		JsonArray pParams;
		try {
			pParams = jsRoot.get("params").getAsJsonArray();
		} catch (Exception ex) {
			return ErrorMes(104, "Bad format JSON - not 'params'");
		}
		if (pParams == null) {
			return ErrorMes(104, "Bad format JSON - not 'params'");
		}

		String sReq = "";
		boolean godPodParam = false;
		String sPodType = "";
		String idWidget = "";
		try {
			sPodType = pParams.get(0).getAsString();
			idWidget = pParams.get(1).getAsString();
		} catch (Exception ex) {
			return ErrorMes(105, "Bad format JSON - bad 'params'");
		}

		if (sPodType.equals("Form")) {
			godPodParam = true;

			if (idWidget.equals("app")) {
				// sReq = "{\"id\": \"app\", \r\n\"rows\": [" + CurrentForm.getStrJson() + "]}";
				sReq = CurrentForm.getStrJson();
			} else {
				for (Ui_Abstract form : listForm) {
					sReq = form.getUiJson(idWidget);
					if (!sReq.equals("")) {
						break;
					}
				}

			}

			// sReq = RefWidgetForm(idWidget);
		}

		if (sPodType.equals("Val")) {
			godPodParam = true;
			sReq = RefWidgetVal(idWidget);
		}

		if (sPodType.equals("SetHTML")) {
			godPodParam = true;
			sReq = RefWidgetSetHTML(idWidget);
		}

		if (!godPodParam) {
			return ErrorMes(106, "Bad format JSON - bad 'params' podtype");
		}
		if (sReq.equals("")) {
			return ErrorMes(107, "not Widget id = " + idWidget);
		}
		JsonObject jsReq = new JsonObject();
		jsReq.addProperty("command", "RefWidget");
		jsReq.addProperty("id", idWidget);
		jsReq.addProperty("type", "Form");
		jsReq.addProperty("data", sReq);
		return OkMes(jsReq);
	}

	public abstract String sesReqParse(JsonObject jsRoot, String sType);

	// получить значение из очереди обновления экрана
	private String quReqQv(JsonObject jsRoot) {
		// сейчас просто обновление времени на экране
		JsonObject com = new JsonObject();
		com.addProperty("command", "RefWidget");
		com.addProperty("id", "time");
		com.addProperty("type", "SetHTML");
		com.addProperty("data",
				"<div class='toolbar-time'>" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "</div>");
		return OkMes(com);
	}

	// ответ - ошибка
	public String ErrorMes(int nom, String mes) {
		JsonObject jsResp = new JsonObject();
		jsResp.addProperty("result", "");
		jsResp.addProperty("error", nom);
		jsResp.addProperty("errormes", mes);
		jsResp.addProperty("id", "1");
		return jsResp.toString();
	}

	// ответ - Ok!
	public String OkMes(JsonObject com) {
		JsonObject jsResp = new JsonObject();
		jsResp.add("result", com);
		jsResp.addProperty("error", 0);
		jsResp.addProperty("id", 1);
		return jsResp.toString();
	}

	// ответ - уведомление принято
	public String eventOk() {
		JsonObject com = new JsonObject();
		com.addProperty("event", "Ок");
		return OkMes(com);
	}
}
