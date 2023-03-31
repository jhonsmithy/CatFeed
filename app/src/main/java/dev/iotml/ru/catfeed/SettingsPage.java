package dev.iotml.ru.catfeed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsPage extends AppCompatActivity implements View.OnClickListener{
    EditText url_edit;
    Button button_save;
    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        url_edit = (EditText) findViewById(R.id.url_edit);
        button_save=(Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(this);
        load_text();
    }

    //читаем настройки из памяти
    private void load_text() {
        sPref = getSharedPreferences("Settings.ini",MODE_PRIVATE);
        String url_load_text = sPref.getString("url_address","192.168.4.1");
        url_edit.setText(url_load_text);
    }

    @Override
    public void onClick(View v) {
        save_text();//сохранить
        Intent intent = new Intent();
        intent.putExtra("name",url_edit.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    private void save_text() {
        sPref = getSharedPreferences("Settings.ini",MODE_PRIVATE);
        SharedPreferences.Editor ed= sPref.edit();
        ed.putString("url_address",url_edit.getText().toString());
        ed.commit();
    }

}