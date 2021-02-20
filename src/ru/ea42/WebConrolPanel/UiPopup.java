package ru.ea42.WebConrolPanel;

public class UiPopup extends Ui_Abstract {
    public UiPopup(Ui_Abstract Owner, String head) {
        super(Owner);
        this.Head = head;
    }

    public UiPopup(AbstractSession Owner, String head) {
        super(Owner);
        this.Head = head;
    }

    public String Head;

    public Ui_Abstract Ui;

    @Override
    public String getView() {
        return "popup";
    }

    @Override
    protected String getStrJsonSub() {
        return "\"head\": \"" + Head + "\", \r\n \"body\":" + Ui.getStrJson();
    }

    @Override
    protected String getUiJsonSub(String idUi) {
        return Ui.getUiJson(idUi);
    }

    @Override
    public void AddUi(Ui_Abstract ui) {
        Ui = ui;
    }
}