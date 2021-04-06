package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SuccessRegisterAct extends AppCompatActivity {

    Animation splash_anim, btt, ttb;

    ImageView img_success_reg;
    TextView tittle_success_reg, subtittle_success_reg;
    Button btn_explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        // ================================================================
        img_success_reg = findViewById(R.id.img_success_reg);
        tittle_success_reg = findViewById(R.id.tittle_success_reg);
        subtittle_success_reg = findViewById(R.id.subtittle_success_reg);
        btn_explore = findViewById(R.id.btn_explore);
        // ================================================================


        // ============================================================================
        splash_anim = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        // ============================================================================


        // ===================================================
        img_success_reg.startAnimation(splash_anim);
        tittle_success_reg.startAnimation(ttb);
        subtittle_success_reg.startAnimation(ttb);
        btn_explore.startAnimation(btt);
        // ===================================================


        // =================================================================================================
        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohomeact = new Intent(SuccessRegisterAct.this,HomeAct.class);
                startActivity(gotohomeact);
            }
        });
        // ==================================================================================================
    }
}