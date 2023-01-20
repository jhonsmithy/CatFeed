package dev.iotml.ru.catfeed;

import android.app.Application;

import java.util.ArrayList;

public class GlobalClass
{
    private static GlobalClass instance = new GlobalClass();

    // Getter-Setters
    public static GlobalClass getInstance() {
        return instance;
    }

    public static void setInstance(GlobalClass instance) {
        GlobalClass.instance = instance;
    }

    //список адресов устройств в сети
    private ArrayList<String> list_devices=new ArrayList<String>();


    private GlobalClass() {

    }


    public ArrayList<String> getList() {
        return list_devices;//вернуть список устройств
    }


    public void addToList(String item) {//добавить устройство к списку
        this.list_devices.add(item);
    }
}
