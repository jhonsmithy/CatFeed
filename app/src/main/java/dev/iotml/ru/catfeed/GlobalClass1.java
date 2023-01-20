package dev.iotml.ru.catfeed;

import android.app.Application;

import java.util.ArrayList;

public class GlobalClass1
{
    private static GlobalClass1 instance = new GlobalClass1();

    // Getter-Setters
    public static GlobalClass1 getInstance() {
        return instance;
    }

    public static void setInstance(GlobalClass1 instance) {
        GlobalClass1.instance = instance;
    }

    private String notification_index="ini";


    private GlobalClass1() {

    }


    public String getValue() {
        return notification_index;
    }


    public void setValue(String notification_index) {
        this.notification_index = notification_index;
    }
}
