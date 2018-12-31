package com.example.stanislav.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stanislav.myapplication.R;
import com.example.stanislav.myapplication.SpeeerApplication;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "com.stanislav.SHOW_LOGIN_ACTIVITY";
    public static final String SERVER_URL = "serverUrl";

    private EditText serverURL;
    private SpeeerApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverURL = findViewById(R.id.serverUrl);
        application = (SpeeerApplication) getApplication();
        application.setSharedPreferences(getPreferences(MODE_PRIVATE));
        loadText();
    }

    public void onClick(View view) {
        saveURL();
        application.createRetrofit(serverURL.getText().toString());
        Intent intent = new Intent(ACTION);
        startActivity(intent);
    }

    private void saveURL() {
        application.savePreferences(SERVER_URL, serverURL.getText().toString());
    }

    private void loadText() {
        String savedText = application.loadPreferences(SERVER_URL);
        serverURL.setText(savedText);
        Toast.makeText(this, "URL: " + savedText, Toast.LENGTH_SHORT).show();
    }
}
