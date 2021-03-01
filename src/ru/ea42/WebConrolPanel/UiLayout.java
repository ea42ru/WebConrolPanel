package ru.ea42.WebConrolPanel;

import java.util.ArrayList;

public class UiLayout extends Ui_Abstract {
    public UiLayout(Ui_Abstract Owner, boolean Gorizont) {
        super(Owner);
        Orientation = "rows";
        if (Gorizont) {
            Orientation = "cols";
        }
    }

    public UiLayout(AbstractSession Owner, boolean Gorizont) {
        super(Owner);
        Orientation = "rows";
        if (Gorizont) {
            Orientation = "cols";
        }
    }

    private String Orientation;
    public ArrayList<Ui_Abstract> Uis = new ArrayList<Ui_Abstract>();

    @Override
    public String getView() {
        return "";
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
        return "\"" + Orientation + "\": [" + sReq + "]";
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