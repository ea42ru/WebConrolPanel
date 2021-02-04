package ru.ea42.WebConrolPanel;

public class Demo_Start {
    public static void main(String[] args) {
        WCP wcp = new WCP(Demo_Session.class);
        wcp.Port = 8080;
        wcp.start();
        //wcp.join();
        //wcp.stop();
    }
}
