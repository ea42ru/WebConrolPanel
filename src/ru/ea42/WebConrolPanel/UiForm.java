package ru.ea42.WebRemoteControl;

import java.util.ArrayList;

public class UiForm extends Ui_Abstract {
	public UiForm(Ui_Abstract Owner) {
		super(Owner);
	}

	public UiForm(AbstractSession Owner) {
		super(Owner);
	}

	public ArrayList<Ui_Abstract> Uis = new ArrayList<Ui_Abstract>();

	@Override
	public String getView() {
		return "form";
	}

	@Override
	protected String getStrJsonSub() {
		String sReq = "";
		for (Ui_Abstract ui : Uis) {
			if (!sReq.equals("")) {
				sReq = sReq + ", \r\n";
			}
			sReq = sReq + ui.getStrJson();
		}
		return "\"elements\": [" + sReq + "]";
	}

	@Override
	protected String getUiJsonSub(String idUi) {
		String sReq = "";
		for (Ui_Abstract ui : Uis) {
			sReq = ui.getUiJson(idUi);
			if (!sReq.equals("")) {
				break;
			}
		}
		return sReq;
	}

	@Override
	public void AddUi(Ui_Abstract ui) {
		Uis.add(ui);
	}
}
