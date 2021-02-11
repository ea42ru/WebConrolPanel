package ru.ea42.WebConrolPanel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WCP implements Runnable {
    public int Port = 8282;
    public int MaxSocketProcessor = 2;
    public int MaxSession = 10;
    public int MaxBufSocket = 100;
    private boolean IsWork = true;
    private Class<?> SessionClass = null;
    private ServerSocket SS = null;
    private SocketProcessor[] Msp;
    private AbstractSession[] Mses;
    private BufSocket bufSocket;

    public WCP(Class<?> sessionClass) {
        this.SessionClass = sessionClass;
    }

    public synchronized void start() {
        bufSocket = new BufSocket(MaxBufSocket);
        Mses = new AbstractSession[MaxSession];
        for (int i = 0; i < MaxSession; i++) {
            Mses[i] = null;
        }

        Msp = new SocketProcessor[MaxSocketProcessor];
        for (int i = 0; i < MaxSocketProcessor; i++) {
            Msp[i] = new SocketProcessor(bufSocket, Mses, SessionClass);
        }

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
            if (Scl != null) {
                bufSocket.addSocket(Scl);
            }
        }

        for (int i = 0; i < MaxSocketProcessor; i++) {
            Msp[i].stop();
            Msp[i] = null;
        }
        for (int i = 0; i < MaxSession; i++) {
            Mses[i] = null;
        }
    }

}
