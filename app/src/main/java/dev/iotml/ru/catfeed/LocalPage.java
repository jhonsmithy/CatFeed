package dev.iotml.ru.catfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class LocalPage extends AppCompatActivity implements View.OnClickListener {
    private WebView webView = null;
    String url_address="192.168.25.103";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_page);
        //блок работы с браузером
        this.webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webView.setWebViewClient(webViewClient);
        //показать сообщение с адресом подключения
        Toast.makeText(this, "http://"+url_address, Toast.LENGTH_SHORT).show();
        webView.loadUrl("http://"+url_address);//подключится по адресу
    }

    @Override
    public void onClick(View v) {
        //показать сообщение с адресом подключения
        Toast.makeText(this, "http://"+url_address, Toast.LENGTH_SHORT).show();
        webView.loadUrl("http://"+"192.168.25.103");//подключится по адресу
    }
}