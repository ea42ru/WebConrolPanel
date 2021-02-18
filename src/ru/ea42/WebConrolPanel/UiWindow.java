package ru.ea42.WebConrolPanel;

import java.util.ArrayList;

public class UiWindow extends Ui_Abstract {
    public UiWindow(Ui_Abstract Owner, String head) {
        super(Owner);
        this.Head = head;
    }

    public UiWindow(AbstractSession Owner, String head) {
        super(Owner);
        this.Head = head;
    }

    public String Head;

    public Ui_Abstract Ui;

    @Override
    public String getView() {
        return "window";
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