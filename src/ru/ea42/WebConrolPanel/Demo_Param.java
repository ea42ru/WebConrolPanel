package ru.ea42.WebConrolPanel;

public class Demo_Param {
    public static Demo_Param instance;

    public static synchronized Demo_Param getInstance() {
        if (instance == null) {
            instance = new Demo_Param();
        }
        return instance;
    }

    private static boolean IsWork = true;
    private static int Count = 0;
    private static boolean IsCount = true;

    public static synchronized boolean getIsWork() {
        return IsWork;
    }

    public static synchronized void setIsWork(boolean val) {
        IsWork = val;
    }

    public static synchronized int getCount() {
        return Count;
    }

    public static synchronized void setCount(int val) {
        Count = val;
    }

    public static synchronized boolean getIsCount() {
        return IsCount;
    }

    public static synchronized void setIsCount(boolean val) {
        IsCount = val;
    }
}
