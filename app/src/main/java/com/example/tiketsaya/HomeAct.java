package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_tiket_pisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_tiket_pisa = findViewById(R.id.btn_tiket_pisa);

        btn_tiket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gototiketpisa = new Intent(HomeAct.this,TicketDetailAct.class);
                startActivity(gototiketpisa);
            }
        });
    }
}