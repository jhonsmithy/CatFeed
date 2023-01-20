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
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class AddDevice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        Button button =findViewById(R.id.button);//Находим кнопку
        button.setOnClickListener(new View.OnClickListener(){//Обработчик события - нажатие на кнопку
            @Override     public void onClick(View v) {
                Log.d("LOG_TAG","click");
                final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                ScanReceiver scanReceiver = new ScanReceiver();
                registerReceiver(scanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                wifi.startScan();
            } });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        //WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //List<WifiConfiguration> list = manager.getConfiguredNetworks();
    }

    static  public class App extends Application {
        private WiFiMonitor mWiFiMonitor;
        @Override
        public void onCreate() {
            super.onCreate();
            mWiFiMonitor = new WiFiMonitor();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);//Произошло изменение сетевого подключения.
            registerReceiver(mWiFiMonitor,intentFilter);
        }
        @Override
        public void onTerminate() {// Метод предназначен для использования в эмулируемых средах обработки.
            super.onTerminate();
            unregisterReceiver(mWiFiMonitor);
        }
    }

    static class WiFiMonitor extends BroadcastReceiver {//Базовый класс для кода, который принимает и обрабатывает трансляции, отправленные sendBroadcast (Intent).

        private String LOG_TAG = "myWiFiMonitor";
        @Override     public void onReceive(Context context, Intent intent) {//вызывается, когда BroadcastReceiver получает трансляцию Intent.
            String action = intent.getAction();//Применяйте методы getAction() и getData(), чтобы найти действие и данные, связанные с намерением
            //Действие в объекте Intent устанавливается в методе setAction() и читается методом getAction();
            Log.d(LOG_TAG, action);//Считываем действие
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(CONNECTIVITY_SERVICE);//Получаем объект класса ConnectivityManager, который следит за состоянием сети
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();//Получаем объект класса NetworkInfo для получения описания состояния сети
            boolean isConnected = activeNetwork != null &&  activeNetwork.isConnectedOrConnecting();//Проверяем подключение
            Log.d(LOG_TAG,"isConnected: "+isConnected);
            Toast.makeText(context, "isConnected: "+isConnected, Toast.LENGTH_LONG).show();

            if (!isConnected)
                return;
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;//Проверяем это Wifi или нет.
            Log.d(LOG_TAG,"isWiFi: "+isWiFi);
            Toast.makeText(context, "isWiFi: "+isWiFi, Toast.LENGTH_LONG).show();
            if (!isWiFi)
                return;
            WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);// Используем getSystemService (Class) для извлечения WifiManager для управления доступом Wi-Fi.
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();//Возвращает динамическую информацию о текущем соединении Wi-Fi, если таковая активна.
            Log.d(LOG_TAG,connectionInfo.getSSID());
            Toast.makeText(context, "Connected to Internet: "+connectionInfo.getSSID(), Toast.LENGTH_LONG).show();
        }
    }

    static class ScanReceiver extends BroadcastReceiver {
        private String LOG_TAG = " ";
        @Override     public void onReceive(Context context, Intent intent) {
            WifiManager wifi = (WifiManager) context.getSystemService(WIFI_SERVICE);//Извлекаем WifiManager для управления доступом Wi-Fi.
            List scanResultList = wifi.getScanResults();//Возвращает результаты последнего сканирования точки доступа.
            for (Object scanResult : scanResultList){
                Log.d(LOG_TAG,scanResult.toString());
                Toast.makeText(context, "click"+scanResult.toString(), Toast.LENGTH_LONG).show();
            }
            context.unregisterReceiver(this);
        }
    }
}