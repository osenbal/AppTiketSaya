package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Objects;

public class TicketDetailAct extends AppCompatActivity {


    LinearLayout btn_back;
    Button btn_buy_ticket;
    TextView tittle_ticket, location_ticket,
            photo_spot_ticket, wifi_ticket,
            festival_ticket, short_desc_ticket;
    ImageView header_ticket_detail;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        // ================== Mengambil data dari Intent ===================
        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru = bundle.getString("jenis_tiket");
        // =================================================================


        // =================================================================
        btn_back = findViewById(R.id.btn_back);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        tittle_ticket = findViewById(R.id.tittle_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        festival_ticket= findViewById(R.id.festival_ticket);
        short_desc_ticket= findViewById(R.id.short_desc_ticket);
        header_ticket_detail= findViewById(R.id.header_ticket_detail);
        // =================================================================


        // ============================ Mengambil data dari firebase berdasarkan intent ========================================
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // meminta data yang ada dengan data yang baru
                tittle_ticket.setText(snapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(snapshot.child("lokasi").getValue().toString());
                photo_spot_ticket.setText(snapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(snapshot.child("is_wifi").getValue().toString());
                short_desc_ticket.setText(snapshot.child("short_desc").getValue().toString());
                festival_ticket.setText(snapshot.child("is_festival").getValue().toString());

                // -------------------------------------------------------------------------
                Picasso.with(TicketDetailAct.this).load(Objects.requireNonNull
                        (snapshot.child("url_thumbnail").getValue())
                        .toString()).centerCrop().fit().into(header_ticket_detail);
                // -------------------------------------------------------------------------
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // =======================================================================================================================


        // =======================================================================================================================
        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocheckout = new Intent(TicketDetailAct.this,TicketCheckoutAct.class);
                // Meletakan data pada Intent
                gotocheckout.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotocheckout);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Intent backtodashboard = new Intent(TicketDetailAct.this,HomeAct.class);
//                startActivity(backtodashboard);
            }
        });
        // =========================================================================================================================
    }
}