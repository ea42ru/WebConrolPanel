package ru.ea42.WebConrolPanel;

public class UiLabel extends Ui_Abstract {
    public UiLabel(Ui_Abstract Owner, String value) {
        super(Owner);
        this.Value = value;
    }

    public String Value;

    @Override
    public String getView() {
        return "label";
    }

    @Override
    protected String getStrJsonSub() {
        return "\"label\": \"" + Value + "\"";
    }

    @Override
    protected String getUiJsonSub(String idUi) {
        return "";
    }

    @Override
    public void AddUi(Ui_Abstract ui) {
    }
}