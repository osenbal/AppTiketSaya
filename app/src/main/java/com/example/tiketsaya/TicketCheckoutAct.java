package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class TicketCheckoutAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_buy_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        btn_back = findViewById(R.id.btn_back);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoTicketDetail = new Intent(TicketCheckoutAct.this,TicketDetailAct.class);
                startActivity(backtoTicketDetail);

            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoSuccessBuyTicket = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                startActivity(gotoSuccessBuyTicket);
            }
        });
    }
}