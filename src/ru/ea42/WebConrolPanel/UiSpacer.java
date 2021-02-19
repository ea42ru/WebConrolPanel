package ru.ea42.WebConrolPanel;

public class UiSpacer extends Ui_Abstract {
    public UiSpacer(Ui_Abstract Owner) {
        super(Owner);
    }

    @Override
    public String getView() {
        return "";
    }

    @Override
    protected String getStrJsonSub() {
        return "";
    }

    @Override
    protected String getUiJsonSub(String idUi) {
        return "";
    }

    @Override
    public void AddUi(Ui_Abstract ui) {
    }
}