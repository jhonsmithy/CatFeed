package dev.iotml.ru.catfeed;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

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



        Runnable runnable = new Runnable() {
            public void run() {
                // Переносим сюда старый код
                System.out.println(getClass().getName() + ">>> !!! RUN Thread UDP Sniffer");
                UdpBrowser UB = new UdpBrowser();
                UB.receive_data();

                /*
                //String response="";
                //create socket to transport data
                byte[] receiveData = new byte[1024];
                DatagramSocket clientSocket = null;
                try {
                    clientSocket = new DatagramSocket(4210);

                } catch (SocketException e) {
                    Log.d("ssdp", "Socket Exception thrown when creating socket to transport data");
                    e.printStackTrace();
                }
                System.out.println(getClass().getName() + ">>> CREATE !!! clientSocket") ;
                // receive data
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                System.out.println(getClass().getName() + ">>> CREATE !!! DatagramPacket");
                try {
                    if (clientSocket != null) {
                        while(true){

                        clientSocket.receive(receivePacket);
                            System.out.println(getClass().getName() + ">>> CHECK receivePacket "+receivePacket.getAddress().getHostAddress());
                            SystemClock.sleep(1000); //ms
                            //Log.d("ssdp","Checking target package to see if its empty on iteration#: ");
                        }
                    }else {System.out.println(getClass().getName() + ">>> NULL !!! clientSocket is null object");}
                } catch (IOException e) {
                    Log.d("ssdp","IOException thrown when receiving data");
                    e.printStackTrace();
                }
                //the target package should not be empty
                //try three times

                for (int i =0; i<3; i++){
                    Log.d("ssdp","Checking target package to see if its empty on iteration#: "+i);
                    response = new String(receivePacket.getData());
                    Log.d("ssdp","Response contains: "+response);
                    if (response.contains("Location:")){
                        break;
                    }
                }

/*
                SSDPSocket sock= null;
                //UdpBrowser UB = new UdpBrowser();
                try {
                    sock = new SSDPSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long endTime = System.currentTimeMillis()
                        + 20 * 1000;
                DatagramPacket dp = null; //crashes here**
                Log.i("Thread", "Сегодня коты перебегали дорогу:  раз");
                while (true) {
                    synchronized (this) {
                        try {
                            wait(200);
                            //UB.read_udp_paket();
                            dp = sock.receive();
                            //String c = new String(dp.getData());
                            //if (c!="")
                            Log.i("Thread", "Сегодня коты перебегали дорогу:  раз");
                        } catch (Exception e) {
                        }
                    }
                }
*/
                    // Нельзя!
                    // TextView infoTextView =
                    //         (TextView) findViewById(R.id.textViewInfo);
                    // infoTextView.setText("Сегодня коты перебегали дорогу: " + mCounter++ + " раз");

                /*try {

                    sock = new SSDPSocket();
                    while (true) {
                        DatagramPacket dp = sock.receive(); //crashes here**
                        String c = new String(dp.getData());
                        if (c!=""){
                        System.out.println("RECEIVE UDP!!! "+c);}

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/

                    // Нельзя!
                    // TextView infoTextView =
                    //         (TextView) findViewById(R.id.textViewInfo);
                    // infoTextView.setText("Сегодня коты перебегали дорогу: " + mCounter++ + " раз");
                };
            };
        Thread thread = new Thread(runnable);
        thread.start();




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
            //открыть страницу настроек
            Toast.makeText(this, "Settings open", Toast.LENGTH_SHORT).show();
            //Create new activity
            Intent intent = new Intent(this, SettingsPage.class);
            startActivityForResult(intent,1);
        } else
        if (id == R.id.action_local) {
            //открыть страницу со списком устройств
            //System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            UdpBrowser UB = new UdpBrowser();
            UB.Mainfunc();
            Toast.makeText(this, "UPNP find"+UB.ReciveMsg, Toast.LENGTH_SHORT).show();
            //Create new activity
            //Intent intent = new Intent(this, UdpBrowser.class);
        } else
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