package dev.iotml.ru.catfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DeviceList extends FragmentActivity implements DeviceListFragment.FragmentToActivity {
    private static final String TAG = "DevList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
    }


    @Override
    public void callBack(String data) {
        //здесь можно обработать данные переданные от фрагмента
        Log.e(TAG, "Open local device from IP: "+ data);
        Intent intent = new Intent(this, LocalPage.class);
        startActivityForResult(intent,1);
    }
}