package com.example.sachin.electricityusage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sachin.electricityusage.R;


/**
 * Created by sachin on 22/3/17.
 */

public class SplashActivity extends AppCompatActivity{

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread thread = new Thread(){

            @Override
            public void run() {

                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };

        thread.start();

    }
}
