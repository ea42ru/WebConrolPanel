package ru.ea42.WebConrolPanel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WCP implements Runnable {
    public int Port = 8282;
    public int MaxSocketProcessor = 50;
    public int MaxSession = 10;
    private Class<?> SessionClass = null;
    private boolean IsWork = true;
    private ServerSocket SS = null;
    private SocketProcessor[] Mss;
    private AbstractSession[] Mses;

    public WCP(Class<?> sessionClass) {
        SessionClass = sessionClass;
    }

    public synchronized void start() {
        Mss = new SocketProcessor[MaxSocketProcessor];
        Mses = new AbstractSession[MaxSession];
        if (SS == null) {
            try {
                SS = new ServerSocket(Port);
            } catch (IOException e) {
                e.printStackTrace();
                SS = null;
            }
        }
    }

    public synchronized void stop() {
        IsWork = false;
        if (SS != null) {
            SS = null;
        }
    }

    private synchronized boolean IsWorked() {
        return IsWork;
    }

    @Override
    public void run() {
        Socket Scl;
        while (IsWorked()) {
            if (SS == null) {
                continue;
            }

            Scl = null;
            try {
                Scl = SS.accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            if (!IsWorked()) {
                break;
            }

            System.err.println("Client accepted");
            //new Thread(new SocketProcessor(s)).start();

            for (int i = 0; i < MaxSocketProcessor; i++) {
                if (Mss[i] == null) {
                    Mss[i] = new SocketProcessor(Mses, SessionClass);
                    Mss[i].handleSoc(Scl);
                    continue;
                }
                if (Mss[i].isAviable()) {
                    Mss[i].handleSoc(Scl);
                    continue;
                }
            }
        }

        for (int i = 0; i < MaxSocketProcessor; i++) {
            Mss[i] = null;
        }
        for (int i = 0; i < MaxSession; i++) {
            Mses[i] = null;
        }
    }

}
