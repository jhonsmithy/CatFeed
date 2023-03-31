package dev.iotml.ru.catfeed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{

    private WebView webView = null;
    String url_address;
    private EditText url;

    private boolean isClick = false;
    private Context context;
    private WiFiScanner wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         Button conect= (Button) findViewById(R.id.but_wifi);
        //load_settings();//звгрузить сохраненные настройки

        //блок работы с UDP
        UdpBrowser UB = new UdpBrowser();

        conect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("wifi","click");

            }
        });
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