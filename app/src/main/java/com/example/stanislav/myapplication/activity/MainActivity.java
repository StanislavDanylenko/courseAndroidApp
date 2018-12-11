package com.example.stanislav.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stanislav.myapplication.R;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "com.stanislav.SHOW_LOGIN_ACTIVITY";
    public static final String SERVER_URL = "serverUrl";

    SharedPreferences sharedPreferences;
    EditText serverURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverURL = findViewById(R.id.serverUrl);
        loadText();
    }

    public void onClick(View view) {
        saveURL();
        Intent intent = new Intent(ACTION);
        startActivity(intent);
    }

    private void saveURL() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SERVER_URL, serverURL.getText().toString());
        ed.commit();
    }

    private void loadText() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String savedText = sharedPreferences.getString(SERVER_URL, "");
        serverURL.setText(savedText);
        Toast.makeText(this, "URL: " + savedText, Toast.LENGTH_SHORT).show();
    }
}
