package ru.ea42.WebConrolPanel;

import java.util.HashMap;
import java.util.Map;

public abstract class Ui_Abstract {
    private Ui_Abstract OwnerUi = null;
    private AbstractSession OwnerSes = null;

    public Ui_Abstract(Ui_Abstract owner) {
        OwnerUi = owner;
        // id = "u" + embWRC.GetNextId();
        id = owner.GetNextId();
        owner.AddUi(this);
    }

    public Ui_Abstract(AbstractSession owner) {
        OwnerSes = owner;
        // id = "u" + embWRC.GetNextId();
        id = owner.GetNextId();
        owner.addForm(this);
    }

    protected String GetNextId() {
        if (OwnerUi == null) {
            return OwnerSes.GetNextId();
        }
        return OwnerUi.GetNextId();
    }

    public String id;
    public Map<String, String> paramsStr = new HashMap<String, String>();
    public Map<String, Integer> paramsInt = new HashMap<String, Integer>();
    public Map<String, Boolean> paramsBool = new HashMap<String, Boolean>();

    public abstract String getView();

    protected abstract String getStrJsonSub();

    protected abstract String getUiJsonSub(String idUi);

    public abstract void AddUi(Ui_Abstract ui);

    public String getUiJson(String idUi) {
        if (idUi.equals(id)) {
            return getStrJson();
        }
        return getUiJsonSub(idUi);
    }

    public String getStrJson() {
        String sReq = "{";
        String sReqPl = getView();
        if (!sReqPl.equals("")) {
            sReq = sReq + "\"view\":\"" + sReqPl + "\", \r\n";
        }

        if (OwnerUi == null) {
            sReq = sReq + "\"id\": \"app\"";
        } else {
            sReq = sReq + "\"id\": \"" + id + "\"";
        }

        for (Map.Entry<String, Integer> entry : paramsInt.entrySet()) {
            sReq = sReq + ", \r\n" + "\"" + entry.getKey() + "\":" + entry.getValue();
        }

        for (Map.Entry<String, Boolean> entry : paramsBool.entrySet()) {
            if (entry.getValue()) {
                sReq = sReq + ", \r\n" + "\"" + entry.getKey() + "\":true";
            } else {
                sReq = sReq + ", \r\n" + "\"" + entry.getKey() + "\":false";
            }
        }

        for (Map.Entry<String, String> entry : paramsStr.entrySet()) {
            sReq = sReq + ", \r\n" + "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"";
        }

        sReqPl = getStrJsonSub();
        if (!sReqPl.equals("")) {
            sReqPl = ", \r\n" + sReqPl;
        }
        sReq = sReq + sReqPl + "}";

        return sReq;
    }

    public void addParam(String Key, String Value) {
        paramsStr.put(Key, Value);
    }

    public void addParam(String Key, int Value) {
        paramsInt.put(Key, Value);
    }

    public void addParam(String Key, Boolean Value) {
        paramsBool.put(Key, Value);
    }
}