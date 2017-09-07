package com.smarttrainreservation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.smarttrainreservation.seatBookingrecyclerView.MainActivitySeats;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                finish();
               // startActivity(new Intent(SplashActivity.this,MainActivity.class));
              startActivity(new Intent(SplashActivity.this,EnterTrainInformation.class));




            }
        },2000);
    }
}
