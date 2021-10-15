package ru.ea42.WebRemoteControl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.component.Dumpable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataHandler extends AbstractHandler {
	public static void start(Object arg0) {
	}

	public static void stop(Object arg0) {
	}

	public static String dump(Dumpable arg0) {
		return null;
	}

	public static void dumpObject(Appendable arg0, Object arg1) throws IOException {
	}

	public static void dumpObjects(Appendable arg0, String arg1, Object arg2, Object[] arg3) throws IOException {
	}

	public String dumpSelf() {
		return null;
	}

	public SessionManager sm;

	// принимаем запрос
	public synchronized void handle(String target, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		int ch;
		String StrReq = "";
		ServletInputStream is = request.getInputStream();
		ByteArrayOutputStream sb = new ByteArrayOutputStream();
		try {
			while ((ch = is.read()) != -1)
				sb.write(ch);
			StrReq = sb.toString("UTF8");
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String StrRes = this.ReqParse(StrReq);
		System.out.println("--> " + request.getMethod() + " " + request.getRequestURI() + " " + StrReq);
		System.out.println("<-- " + StrRes);
		System.out.println(" ");

		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(StrRes);
		baseRequest.setHandled(true);
	}

	// парсим запрос
	public String ReqParse(String StrReq) {
		// запрос JSON?
		if (StrReq.equals("")) {
			return ErrorMes(100, "Request not JSON");
		}

		JsonObject jsRoot;
		try {
			jsRoot = new JsonParser().parse(StrReq).getAsJsonObject();
		} catch (Exception ex) {
			return ErrorMes(100, "Request not JSON");
		}

		// запрос верного формата?
		String sSes = "";
		try {
			sSes = jsRoot.get("session").getAsString();
		} catch (Exception ex) {
			return ErrorMes(101, "Bad format JSON - not 'session'");
		}

		// current пустой?
		int sCur = 0;
		try {
			sCur = jsRoot.get("current").getAsInt();
		} catch (Exception ex) {
			return ErrorMes(102, "Bad format JSON - not 'current'");
		}
		if (sCur == 0) {
			return ErrorMes(110, "Bad format JSON - current = 0");
		}

		// проверка last
		int sLast = 0;
		try {
			sLast = jsRoot.get("last").getAsInt();
		} catch (Exception ex) {
			return ErrorMes(103, "Bad format JSON - not 'last'");
		}
		AbstractSession ses = sm.getSession(sSes, sLast, sCur);
		if (ses == null) {
			return goRedirect();
		}

		// маршрутизаци¤ по методам
		String sType = "";
		try {
			sType = jsRoot.get("method").getAsString();
		} catch (Exception ex) {
			return ErrorMes(104, "Bad format JSON - not 'method'");
		}

		// нажата кнопка обновить или закрыть
		if (sType.equals("ScrinStop")) {
			ses.inEnterMode = true;
			return eventOk();
		}

		// пересылаем запрос в конкретную сессию
		String sReq = ses.reqParse(jsRoot, sType);
		if (!sReq.equals("")) {
			return sReq;
		}

		// неизвестный тип запроса
		return ErrorMes(111, "Unknown request method");
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

	// ответ - уведомление прин¤то
	public String eventOk() {
		JsonObject com = new JsonObject();
		com.addProperty("event", "ќк");
		return OkMes(com);
	}

	// перенаправить клиента на другую сессию
	public String goRedirect() {
		JsonObject com = new JsonObject();
		com.addProperty("command", "Redirect");
		com.addProperty("data", "/index.html?session=" + sm.getNewSession().id);
		return OkMes(com);
	}

}
