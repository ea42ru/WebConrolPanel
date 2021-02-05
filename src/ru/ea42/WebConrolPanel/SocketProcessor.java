package ru.ea42.WebConrolPanel;

import java.net.Socket;

public class SocketProcessor {
    private Class<?> SessionClass = null;
    private AbstractSession[] Mses;
    private boolean Aviable = true;
    private Socket Soc;

    public SocketProcessor(AbstractSession[] mses, Class<?> sessionClass) {
        Mses = mses;
        SessionClass = sessionClass;
    }

    public synchronized boolean isAviable() {
        return Aviable;
    }

    public synchronized void handleSoc(Socket s) {
        Aviable=false;
        Soc=s;
    }
}
