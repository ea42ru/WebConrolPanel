package ru.ea42.WebConrolPanel;

public class Demo_Work implements Runnable {
    public WCP wsp;

    public Demo_Work(WCP wsp) {
        this.wsp = wsp;
    }

    @Override
    public void run() {
        int Count;
        while (Demo_Param.getIsWork()) {
            if (Demo_Param.getIsCount()) {
                Count = Demo_Param.getCount();
                Count++;
                if (Count > 999) {
                    Count = 0;
                }
                Demo_Param.setCount(Count);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        Demo_Param.setIsWork(false);
    }

    public void start() {
        wsp.start();
    }

    public void stop() {
        Demo_Param.setIsWork(false);
        wsp.stop();
    }

    public void waiting() {
        while (Demo_Param.getIsWork()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
        wsp.stop();
    }
}
