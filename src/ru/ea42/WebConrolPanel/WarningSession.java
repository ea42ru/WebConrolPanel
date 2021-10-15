package ru.ea42.WebRemoteControl;

import com.google.gson.JsonObject;

public class WarningSession extends AbstractSession {
	@Override
	public String sesReqParse(JsonObject jsRoot, String sType) {
		return null;
	}

	@Override
	public String RefWidgetForm(String idWidget) {
		String sForm = "{\"id\": \"app\", \"cols\": [{}, {\"rows\": [{}, "
				+ "{\"view\": \"text\", \"height\": 50, \"width\": 230, \"value\": \"(c) WebRemoteControl v0.80\"}, "
				+ "{\"view\": \"text\", \"height\" : 50, \"width\": 230, \"value\": \"не определен класс сессии...\"}, "
				+ "{}]}, {}]}";
		return sForm;
	}

	@Override
	public String RefWidgetVal(String idWidget) {
		return null;
	}

	@Override
	public String RefWidgetSetHTML(String idWidget) {
		return null;
	}
}
