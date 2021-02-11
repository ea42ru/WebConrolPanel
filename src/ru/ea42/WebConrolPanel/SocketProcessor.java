package ru.ea42.WebConrolPanel;

import java.net.Socket;

public class SocketProcessor implements Runnable {
    private int worked = 1;
    private BufSocket bufSocket;

    public SocketProcessor(BufSocket bufSocket, AbstractSession[] mses, Class<?> sessionClass) {
        this.bufSocket = bufSocket;
    }

    public void stop() {
        setWorked(2);
        while (getWorked() != 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized int getWorked() {
        return worked;
    }

    public synchronized void setWorked(int worked) {
        this.worked = worked;
    }

    @Override
    public void run() {
        Socket curClSocket = null;
        while (getWorked() == 1) {
            curClSocket = bufSocket.getSocket();
            if (curClSocket == null) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            // здесь работа


        }
        setWorked(0);
    }
}
