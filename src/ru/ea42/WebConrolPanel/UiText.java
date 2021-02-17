package ru.ea42.WebConrolPanel;

public class UiText extends Ui_Abstract {
    public UiText(Ui_Abstract Owner, String label) {
        super(Owner);
        this.Label = label;
    }

    public String Value = "value";
    public String Label = "label";

    @Override
    public String getView() {
        return "text";
    }

    @Override
    protected String getStrJsonSub() {
        return "\"value\": \"" + Value + "\", \"label\": \"" + Label + "\"";
    }

    @Override
    protected String getUiJsonSub(String idUi) {
        return "";
    }

    @Override
    public void AddUi(Ui_Abstract ui) {
    }
}