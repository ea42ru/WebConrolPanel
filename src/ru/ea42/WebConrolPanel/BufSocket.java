package ru.ea42.WebConrolPanel;

import java.net.Socket;

public class BufSocket {
    private int maxBufSocket;
    private Socket[] aviableSocket;
    private int curAdd = 0;
    private int curGet = 0;

    public BufSocket(int maxBufSocket) {
        this.maxBufSocket = maxBufSocket;
        aviableSocket = new Socket[maxBufSocket];
    }

    public synchronized void addSocket(Socket scl) {
        int nextAdd = curAdd + 1;
        if (nextAdd >= maxBufSocket) {
            nextAdd = 0;
        }
        if (nextAdd == curGet) {
            return;
        }

        aviableSocket[curAdd] = scl;
        curAdd = nextAdd;
    }

    public synchronized Socket getSocket() {
        Socket scl = null;
        if (curAdd != curGet) {
            scl = aviableSocket[curGet];
            aviableSocket[curGet] = null;
            curGet++;
            if (curGet >= maxBufSocket) {
                curGet = 0;
            }
        }
        return scl;
    }
}
