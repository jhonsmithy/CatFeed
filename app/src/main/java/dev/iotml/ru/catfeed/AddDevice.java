package dev.iotml.ru.catfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddDevice extends AppCompatActivity {


    private static final String TAG = "ADDDEV";

    public  Context context;


    //CallbackFromWifiScanner callbackwifi = new CallbackFromWifiScanner();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        // метод который инициализирует все что нам нужно

        context = this.getBaseContext();
        Button scan = (Button) findViewById(R.id.but_scan_wifi);
        Button connect = (Button) findViewById(R.id.but_connect_wifi);

        WiFiScanner wifi=new WiFiScanner(this);

        scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG,"scan");
                //инициализируем колбек, передавая методу registerCallBack экземпляр, реализующий интерфейс колбек
                //wifi.registerCallBack(callbackwifi);

                wifi.RunScanWifi("IoTmanager");

                //connect.setEnabled(false);

            }
        });


        connect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG,"connect");
                //инициализируем колбек, передавая методу registerCallBack экземпляр, реализующий интерфейс колбек
                //wifi.registerCallBack(callbackwifi);


                wifi.connectToWifi("IoTmanager");
                //connect.setEnabled(false);

            }
        });
    }

}