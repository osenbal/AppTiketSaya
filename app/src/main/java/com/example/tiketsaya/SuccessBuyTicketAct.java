package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessBuyTicketAct extends AppCompatActivity {
    Button btn_mydashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        btn_mydashboard = findViewById(R.id.btn_mydasboard);

        btn_mydashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodashboard = new Intent(SuccessBuyTicketAct.this, HomeAct.class);
                startActivity(gotodashboard);
            }
        });
    }
}