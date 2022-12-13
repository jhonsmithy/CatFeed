package dev.iotml.ru.catfeed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webView = null;
    String url_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load_settings();

        this.webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);
        Toast.makeText(this, "http://"+url_address, Toast.LENGTH_SHORT).show();
        webView.loadUrl("http://"+url_address);//192.168.217.103
    }

    private void load_settings() {
        SharedPreferences stgs = getSharedPreferences("Settings.ini",MODE_PRIVATE);
        url_address = stgs.getString("url_address","192.168.4.1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Toast.makeText(this, "Settings open", Toast.LENGTH_SHORT).show();
            //Create new activity
            Intent intent = new Intent(this, SettingsPage.class);
            startActivityForResult(intent,1);
        } else
        if (id == R.id.action_upnp) {

            //System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            UdpBrowser UB = new UdpBrowser();
            UB.Mainfunc();
            Toast.makeText(this, "UPNP find"+UB.ReciveMsg, Toast.LENGTH_SHORT).show();
            //Create new activity
            //Intent intent = new Intent(this, UdpBrowser.class);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null){
            return;
        }
        url_address=data.getStringExtra("name");
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