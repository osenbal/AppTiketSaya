package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MyTicketDetailAct extends AppCompatActivity {

    LinearLayout btn_back;
    TextView xnama_wisata, xlokasi, xdate_wisata, xtime_wisata, xketentuan;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        // ==================================================
        btn_back = findViewById(R.id.btn_back);
        xnama_wisata = findViewById(R.id.xnama_wisata);
        xlokasi = findViewById(R.id.xlokasi);
        xdate_wisata = findViewById(R.id.xdate_wisata);
        xtime_wisata = findViewById(R.id.xtime_wisata);
        xketentuan = findViewById(R.id.xketentuan);
        // ==================================================


        // ====================== Mengambil data dari Intent =================
        Bundle bundle = getIntent().getExtras();
        String nama_wisata_baru = bundle.getString("nama_wisata");
        // ===================================================================


        // ====================== Mengambil data pada database ================================================
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                xnama_wisata.setText(snapshot.child("nama_wisata").getValue().toString());
                xlokasi.setText(snapshot.child("lokasi").getValue().toString());
                xdate_wisata.setText(snapshot.child("date_wisata").getValue().toString());
                xtime_wisata.setText(snapshot.child("time_wisata").getValue().toString());
                xketentuan.setText(snapshot.child("ketentuan").getValue().toString());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // =====================================================================================================


        // =====================================================================
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // =====================================================================

    }
}