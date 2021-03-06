package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

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

        getUsernameLocal();
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
        if (username_key_new.isEmpty()){
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
        } else{
            // setting timer untuk 1 detik
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //merubah activity ke activity lain
                    Intent gogethome = new Intent(MainActivity.this, HomeAct.class);
                    startActivity(gogethome);
                    finish();
                }
            }, 2000); //2000 ms = 2 detik
        }
    }
}