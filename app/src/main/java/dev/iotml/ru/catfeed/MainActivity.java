package dev.iotml.ru.catfeed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private WebView webView = null;
    String url_address;
    private WifiManager wifiManager;
    private WifiConfiguration wifiConfig;
    private WifiReceiver wifiResiver;
    private boolean wifiEnabled;
    private EditText url;
    private Button conect;
    private boolean isClick = false;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load_settings();//звгрузить сохраненные настройки

        //блок работы с UDP
        UdpBrowser UB = new UdpBrowser();

        init();
        conect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("wifi","click");

                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                getApplicationContext().registerReceiver(wifiResiver, intentFilter);

                boolean success1 = wifiManager.startScan();
                if (!success1) {
                    // scan failure handling
                    scanFailure();
                }
/*
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
                */
            }
        });
    }

    // Подключаемся к wifi указаному в edit text

    public void scheduleSendLocation() {

        registerReceiver(wifiResiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    public void init() {

        conect = (Button) findViewById(R.id.but_wifi);
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

    private void scanSuccess() {
        List<ScanResult> results = wifiManager.getScanResults();
        Log.d("wifi","wifi new scan: "+results.size());
    }

    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        List<ScanResult> results = wifiManager.getScanResults();
        Log.d("wifi","wifi old scan: "+results.size());
    }


    public class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(
                    WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                scanSuccess();
            } else {
                // scan failure handling
                scanFailure();
            }



            /*//сканируем вайфай точки и узнаем какие доступны
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
            }*/
        }
    }

    private void load_settings() {
        //загрузить сохраненные настройки
        SharedPreferences stgs = getSharedPreferences("Settings.ini",MODE_PRIVATE);
        url_address = stgs.getString("url_address","192.168.4.1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // создаем меню
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //открыть страницу настроек
            Toast.makeText(this, "Settings open", Toast.LENGTH_SHORT).show();
            //Create new activity
            Intent intent = new Intent(this, SettingsPage.class);
            startActivityForResult(intent,1);
        } else
        if (id == R.id.action_local) {
            //открыть страницу с интерфейсом устройства в браузере
            Toast.makeText(this, "Local page open", Toast.LENGTH_SHORT).show();
            //Create new activity
            Intent intent = new Intent(this, LocalPage.class);
            startActivityForResult(intent,1);
        } else
        if (id == R.id.action_devices) {
            //открыть страницу со списком устройств
            Toast.makeText(this, "Devices list open", Toast.LENGTH_SHORT).show();
            //Create new activity
            Intent intent = new Intent(this, DeviceList.class);
            startActivityForResult(intent,1);

        } else
            if (id == R.id.action_add_device){
                //открыть страницу со списком устройств
                Toast.makeText(this, "Device add to list", Toast.LENGTH_SHORT).show();
                //Create new activity
                Intent intent = new Intent(this, AddDevice.class);
                startActivityForResult(intent,1);
            }else
            if (id == R.id.action_mqtt){
                // открыть страницу с работой по удаленке через mqtt
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }
        url_address=data.getStringExtra("name");//сохраненный адрес
        Toast.makeText(this, url_address, Toast.LENGTH_SHORT).show();
        webView.loadUrl("http://"+url_address);//192.168.217.103
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}