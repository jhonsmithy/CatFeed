package dev.iotml.ru.catfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
/*
    private WifiManager wifiManager;
    private WifiConfiguration wifiConfig;
    private WifiReceiver wifiResiver;
    private boolean wifiEnabled;
    private EditText url;
    private Button conect;
    private boolean isClick = false;

    public void init() {

        conect = (Button) findViewById(R.id.button);
        url = (EditText) findViewById(R.id.editTextTextPersonName);

        // создаем новый объект для подключения к конкретной точке
        wifiConfig = new WifiConfiguration();
        // сканнер вайфая который нам будет помогать подключаться к нужной точке
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        //узнаем включен вайфай  или нет
        wifiEnabled = wifiManager.isWifiEnabled();

        //наш рессивер который будем подключать нас столько сколько нам понадобиться, пока не будет подключена нужная точка
        wifiResiver = new WifiReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        // метод который инициализирует все что нам нужно
        init();

        conect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("wifi","click");

                //если файвай включен то ничего не делаем иначе включаем его
                if(!wifiEnabled) {
                    Log.d("wifi","wifi connected");
                    wifiManager.setWifiEnabled(true);
                }
                Log.d("wifi","Enabled state");
                //запускаем сканнер вайфая, и подключаемся если подкходящая нам есть есть
                scheduleSendLocation();
                //запускаем рессивер
                isClick = true;
            }
        });
    }



     // Подключаемся к wifi указаному в edit text

    public void scheduleSendLocation() {

        registerReceiver(wifiResiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    protected void onPause() {

        //если приложение уходит в фон или например выключаем его вообще, то и рессивер тормазим и выключаем.
        unregisterReceiver(wifiResiver);
        super.onPause();
    }

    public void onResume() {

        //если кликнули то запускаем рессивер, если же isClick = false то ждем пока кнопка будет нажата
        if(isClick)
            registerReceiver(wifiResiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

     // Рессивер который каждый раз запускает сканнер сети

    public class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context c, Intent intent) {

            //сканируем вайфай точки и узнаем какие доступны
            List<ScanResult> results = wifiManager.getScanResults();
            //проходимся по всем возможным точкам
            //String data = results.get(0).toString();
            Log.d("wifi","wifi scan: "+results.size());
            for (final ScanResult ap : results) {
                Log.d("wifi",ap.SSID.toString());
                //ищем нужную нам точку с помощью ифа, будет находить то которую вы ввели
                if(ap.SSID.toString().trim().equals(url.getText().toString().trim())) {
                    // дальше получаем ее MAC и передаем для коннекрта, MAC получаем из результата
                    //здесь мы уже начинаем коннектиться
                    wifiConfig.BSSID = ap.BSSID;
                    wifiConfig.priority = 1;
                    wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    wifiConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    wifiConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                    wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                    wifiConfig.status = WifiConfiguration.Status.ENABLED;

                    //получаем ID сети и пытаемся к ней подключиться,
                    int netId = wifiManager.addNetwork(wifiConfig);
                    wifiManager.saveConfiguration();
                    //если вайфай выключен то включаем его
                    wifiManager.enableNetwork(netId, true);
                    //если же он включен но подключен к другой сети то перегружаем вайфай.
                    wifiManager.reconnect();
                    break;
                }
            }
        }
    }
*/
}