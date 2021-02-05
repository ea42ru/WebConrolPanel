package ru.ea42.WebConrolPanel;

public class Demo_Start {
    public static void main(String[] args) {
        WCP wsp = new WCP(Demo_Session.class);
        wsp.Port = 8080;

        Demo_Work dw=new Demo_Work(wsp);
        dw.start();
        dw.waiting();
    }
}
