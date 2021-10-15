package ru.ea42.WebRemoteControl;

import com.google.gson.JsonObject;

public class Demo_Session extends AbstractSession {
	public Ui_Abstract FormLogin;
	public Ui_Abstract FormMain;
	public boolean UserLogined;

	public Demo_Session() {
		FormLogin = createFormLogin();
		FormMain = createFormMain();
		CurrentForm = FormLogin;
		UserLogined = false;
	}

	public Ui_Abstract createFormLogin() {
		Ui_Abstract form = new UiWindow(this, "Авторизуйтесь (вход: пароль = логин)");
		form.addParam("position", "center");
		form.addParam("move", true);
		form.addParam("hidden", false);
		form.addParam("width", 400);
		
		UiLayout body = new UiLayout(form, false);
		body.addParam("padding", 15);

		UiText login = new UiText(body, "»м¤");
		login.Value = "ea42@mail.ru";

		UiText pass = new UiText(body, "ѕароль");
		pass.Value = "123";
		pass.addParam("type", "password");

		UiLayout lay2 = new UiLayout(body, true);
		lay2.addParam("margin", 15);
		UiButton bot1 = new UiButton(lay2, "¬ход");
		bot1.addParam("type", "form");
		UiButton bot2 = new UiButton(lay2, "ќтмена");

		UiLabel labTime = new UiLabel(body, "");
		labTime.id = "time";

		return form;
	}

	public Ui_Abstract createFormMain() {
		Ui_Abstract form = new UiForm(this);
		form.addParam("padding", 460);
		new UiLabel(form, "потяни и ты внутри!!!");
		return form;
	}

	@Override
	public String sesReqParse(JsonObject jsRoot, String sType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String RefWidgetVal(String idUi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String RefWidgetSetHTML(String idUi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String RefWidgetForm(String idUi) {
		return null;
	}
}
