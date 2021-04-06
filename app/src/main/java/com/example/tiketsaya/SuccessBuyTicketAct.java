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

public class SuccessBuyTicketAct extends AppCompatActivity {

    Button btn_mydashboard, btn_view_ticket;
    ImageView icon_success_buy;
    TextView tittle_success_buy, subtittle_success_buy;

    Animation splash_anim, btt, ttb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        // ================================================================
        btn_mydashboard = findViewById(R.id.btn_mydasboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        icon_success_buy = findViewById(R.id.icon_success_buy);
        tittle_success_buy = findViewById(R.id.tittle_success_buy);
        subtittle_success_buy = findViewById(R.id.subtittle_success_buy);
        // ================================================================

        // ============================ Load animations ================================
        splash_anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);
        // =============================================================================

        // ================= Run animation ====================
        icon_success_buy.startAnimation(splash_anim);
        tittle_success_buy.startAnimation(ttb);
        subtittle_success_buy.startAnimation(ttb);
        btn_mydashboard.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);
        // ====================================================

        // ====================================================================================================
        btn_mydashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodashboard = new Intent(SuccessBuyTicketAct.this, HomeAct.class);
                startActivity(gotodashboard);
            }
        });

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(SuccessBuyTicketAct.this, MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });
        // ====================================================================================================
    }
}