package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_tiket_pisa, btn_tiket_torri,
            btn_tiket_pagoda, btn_tiket_candi,
            btn_tiket_sphinx, btn_tiket_monas;
    CircleView btn_to_profile;
    ImageView photo_home_user;
    TextView nama_lengkap, bio, user_balance;
    Button btn_cart;

    DatabaseReference reference;
    Integer value_uang = 0;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    // ================= Untuk mengkonversi text jadi Rupiah =================
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    // =======================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // =========== Mengambil data username ===========
        getUsernameLocal();
        // ===============================================


        // ================ Mengambil Element ====================
        btn_cart = findViewById(R.id.btn_cart);
        btn_tiket_pisa = findViewById(R.id.btn_tiket_pisa);
        btn_tiket_torri = findViewById(R.id.btn_tiket_torri);
        btn_tiket_pagoda = findViewById(R.id.btn_tiket_pagoda);
        btn_tiket_candi = findViewById(R.id.btn_tiket_candi);
        btn_tiket_sphinx = findViewById(R.id.btn_tiket_sphinx);
        btn_tiket_monas = findViewById(R.id.btn_tiket_monas);
        btn_to_profile = findViewById(R.id.btn_to_profile);
        photo_home_user = findViewById(R.id.photo_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        user_balance = findViewById(R.id.user_balance);
        bio = findViewById(R.id.bio);
        // ========================================================


        // ==========================================================================================================
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama_lengkap.setText(snapshot.child("nama_lengkap").getValue().toString());
                bio.setText(snapshot.child("bio").getValue().toString());
                //user_balance.setText("Rp " + snapshot.child("user_balance").getValue().toString());
                value_uang = Integer.valueOf(snapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this).load(Objects.requireNonNull
                        (snapshot.child("url_photo_profile").getValue())
                        .toString()).centerCrop().fit().into(photo_home_user);

                user_balance.setText(formatRupiah.format((double)value_uang));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // =============================================================================================================


        // =============================================================================================================
        // ------------------------------------- Button to profile -----------------------------------------
        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent (HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });
        // -------------------------------------------------------------------------------------------------

        // ------------------------------------- Button Tiket Pissa ----------------------------------------
        btn_tiket_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototiketpisa = new Intent(HomeAct.this,TicketDetailAct.class);
                // Meletakan data pada Intent
                gototiketpisa.putExtra("jenis_tiket", "Pisa");
                startActivity(gototiketpisa);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // ------------------------------------- Button Tiket Torri ------------------------------------------
        btn_tiket_torri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototikettorri = new Intent(HomeAct.this,TicketDetailAct.class);
                // Meletakan data pada Intent
                gototikettorri.putExtra("jenis_tiket", "Torri");
                startActivity(gototikettorri);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // ------------------------------------- Button Tiket Pagoda -----------------------------------------
        btn_tiket_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototiketpagoda = new Intent(HomeAct.this,TicketDetailAct.class);
                // Meletakan data pada Intent
                gototiketpagoda.putExtra("jenis_tiket", "Pagoda");
                startActivity(gototiketpagoda);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // ------------------------------------- Button Tiket Candi ------------------------------------------
        btn_tiket_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototiketcandi = new Intent(HomeAct.this,TicketDetailAct.class);
                // Meletakan data pada Intent
                gototiketcandi.putExtra("jenis_tiket", "Candi");
                startActivity(gototiketcandi);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // ------------------------------------ Button Tiket Sphinx ------------------------------------------
        btn_tiket_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototiketsphinx = new Intent(HomeAct.this,TicketDetailAct.class);
                // Meletakan data pada Intent
                gototiketsphinx.putExtra("jenis_tiket", "Sphinx");
                startActivity(gototiketsphinx);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // ------------------------------------- Button Tiket Monas ------------------------------------------
        btn_tiket_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gototiketmonas = new Intent(HomeAct.this,TicketDetailAct.class);
                // Meletakan data pada Intent
                gototiketmonas.putExtra("jenis_tiket", "Monas");
                startActivity(gototiketmonas);
            }
        });
        // ---------------------------------------------------------------------------------------------------

        // =================================== Button to Cart ====================================
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocart = new Intent(HomeAct.this,activity_cart.class);
                startActivity(gotocart);
            }
        });
        // =======================================================================================
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}