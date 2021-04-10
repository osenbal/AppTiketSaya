package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.CookieHandler;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Queue;

public class activity_cart extends AppCompatActivity {

    LinearLayout item_my_ticket_cart, btn_back;
    Button btn_cancel, btn_buy_ticket;
    TextView text_mybalance, text_totalharga;
    ImageView notice_uang;

    DatabaseReference reference, reference2, reference3;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String nama_wisata, lokasi, ketentuan, date_wisata, time_wisata;


    Integer mybalance = 0;
    Integer value_totalharga = 0;
    Integer value_hargatiket = 0;
    Integer value_hargatiket_pisa = 0;
    Integer value_hargatiket_monas = 0;
    Integer value_hargatiket_candi = 0;
    Integer value_hargatiket_sphinx = 0;
    Integer value_hargatiket_torri = 0;
    Integer value_jumlah_ticket = 1;

    RecyclerView myticket_cart;
    ArrayList<MyTicket> list;
    TicketAdapter ticketAdapter;

    // untuk mengkonversi text jadi Rupiah
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // ====================================================================
        getUsernameLocal();
        // ====================================================================

        // ====================================================================
        btn_cancel = findViewById(R.id.btn_cancel);
        item_my_ticket_cart= findViewById(R.id.item_my_ticket_cart);
        myticket_cart = findViewById(R.id.myticket_cart);
        btn_back = findViewById(R.id.btn_back);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        text_mybalance = findViewById(R.id.text_mybalance);
        text_totalharga = findViewById(R.id.text_totalharga);
        notice_uang = findViewById(R.id.notice_uang);
        // ====================================================================


        // ================================== Mengambil data users dari firebase ===========================================
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mybalance = Integer.valueOf(snapshot.child("user_balance").getValue().toString());
                // Uang Saya
                text_mybalance.setText(formatRupiah.format((double)mybalance));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // ====================================================================================================================


        // ====================== Mengambil data wisata dari firebase berdasarkan intent ================================
        reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets_cart").child(username_key_new);
        reference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // meminta data yang ada dengan data yang baru
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren() ){
                    value_hargatiket = Integer.valueOf(String.valueOf(dataSnapshot1.child("cart_total_harga").getValue()));
                    value_totalharga += value_hargatiket;
                }

                // Total harga ticket
                text_totalharga.setText(formatRupiah.format((double)value_totalharga));

                if(value_totalharga > mybalance){
                    btn_buy_ticket.animate().alpha(0)
                            .translationY(250).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    text_mybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }else {
                    notice_uang.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // =================================================================================================================


        // ============================================================================================
        notice_uang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "BOKEK LO", Toast.LENGTH_SHORT).show();
            }
        });
        // ============================================================================================

        
        // =========================================================================================================
        myticket_cart.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyTicket>();
        // =========================================================================================================


        // =========================================================================================================
        reference2 = FirebaseDatabase.getInstance().getReference().child("MyTickets_cart").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    MyTicket p = dataSnapshot1.getValue(MyTicket.class);
                    list.add(p);

                }
                ticketAdapter = new TicketAdapter(activity_cart.this, list);
                myticket_cart.setAdapter(ticketAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // =========================================================================================================


        // ==========================================================================================================================
        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Maaf Masih dalam perbaikan !\n Input : " + value_totalharga, Toast.LENGTH_SHORT).show();

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobacktohome = new Intent(activity_cart.this, HomeAct.class);
                startActivity(gobacktohome);
//                onBackPressed();
            }
        });
        // =============================================================================================================================

    }


    // =================================================================================================================================
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
    // =================================================================================================================================
}