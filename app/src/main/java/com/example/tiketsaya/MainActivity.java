package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation splash_anim, btt;

    ImageView app_logo;
    TextView subtittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load animation
        splash_anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);


        //load element
        app_logo = findViewById(R.id.app_logo);
        subtittle = findViewById(R.id.subtittle);

        // run animation
        app_logo.startAnimation(splash_anim);
        subtittle.startAnimation(btt);

        // setting timer untuk 1 detik
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //merubah activity ke activity lain
                Intent gogetstarted = new Intent(MainActivity.this, GetStartedAct.class);
                startActivity(gogetstarted);
                finish();
            }
        }, 2000); //2000 ms = 2 detik
    }
}