package com.example.proyecto_login.UserInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyecto_login.R;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        timer = new Timer ();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent menuactivity;
                menuactivity = new Intent(StartActivity.this, MenuActivity.class);
                startActivity(menuactivity);
                finish();
            }
        },3000);
    }
}
