package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.florent37.shapeofview.shapes.CircleView;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_tiket_pisa;
    CircleView btn_to_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_tiket_pisa = findViewById(R.id.btn_tiket_pisa);
        btn_to_profile = findViewById(R.id.btn_to_profile);

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent (HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_tiket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gototiketpisa = new Intent(HomeAct.this,TicketDetailAct.class);
                startActivity(gototiketpisa);
            }
        });
    }
}