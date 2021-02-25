package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class TicketCheckoutAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_buy_ticket, btnminus, btnplus;
    TextView text_jumlah_ticket, text_totalharga, text_mybalance;
    ImageView notice_uang;
    Integer value_jumlah_ticket = 1;
    Integer mybalance = 900000;
    Integer value_totalharga = 0;
    Integer value_hargatiket = 200000;


    // untuk mengkonversi text jadi Rupiah
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        // mendapatkan element id dari xml
        btn_back = findViewById(R.id.btn_back);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btnminus = findViewById(R.id.btnminus);
        btnplus = findViewById(R.id.btnplus);
        text_jumlah_ticket = findViewById(R.id.text_jumlah_ticket);
        text_totalharga = findViewById(R.id.text_totalharga);
        text_mybalance = findViewById(R.id.text_mybalance);
        notice_uang = findViewById(R.id.notice_uang);


        // Mengubah value baru untuk beberapa komponen
        text_jumlah_ticket.setText(value_jumlah_ticket.toString());
        // Uang Saya
        text_mybalance.setText(formatRupiah.format((double)mybalance));
        // Total harga ticket
        value_totalharga = value_jumlah_ticket * value_hargatiket;
        text_totalharga.setText(formatRupiah.format((double)value_totalharga));

        // secara default, hide btnminus
        btnminus.animate().alpha(0).setDuration(300).start();
        btnminus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value_jumlah_ticket+=1;
                text_jumlah_ticket.setText(value_jumlah_ticket.toString());
                if (value_jumlah_ticket > 1) {
                    btnminus.animate().alpha(1).setDuration(300).start();
                    btnminus.setEnabled(true);
                }
                value_totalharga = value_jumlah_ticket * value_hargatiket;
                text_totalharga.setText(formatRupiah.format((double)value_totalharga));

                if(value_totalharga > mybalance){
                    btn_buy_ticket.animate().alpha(0)
                            .translationY(250).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    text_mybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);

                }

            }
        });

        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value_jumlah_ticket-=1;
                text_jumlah_ticket.setText(value_jumlah_ticket.toString());
                if (value_jumlah_ticket < 2) {
                    btnminus.animate().alpha(0).setDuration(300).start();
                    btnminus.setEnabled(false);
                }
                value_totalharga = value_jumlah_ticket * value_hargatiket;
                text_totalharga.setText(formatRupiah.format((double)value_totalharga));


                if(value_totalharga < mybalance){
                    btn_buy_ticket.animate().alpha(1)
                            .translationY(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(true);
                    text_mybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);


                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Intent backtoTicketDetail = new Intent(TicketCheckoutAct.this,TicketDetailAct.class);
//                startActivity(backtoTicketDetail);
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