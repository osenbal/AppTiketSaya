package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_buy_ticket, btnminus, btnplus, btn_add_cart;
    TextView text_jumlah_ticket, text_totalharga,
            text_mybalance, nama_wisata, lokasi, ketentuan;
    ImageView notice_uang;

    Integer value_jumlah_ticket = 1;
    Integer mybalance = 0;
    Integer value_totalharga = 0;
    Integer value_hargatiket = 0;
    Integer sisa_balance = 0;
    Integer cart_totalharga = 0;

    // generate nomor integer secara random
    // karena kita ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    String date_wisata ="";
    String time_wisata="";


    DatabaseReference reference, reference2, reference3,
            reference4, reference5, reference6;

    // untuk mengkonversi text jadi Rupiah
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        // ================= Mengambil data dari Intent =====================
        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru = bundle.getString("jenis_tiket");
        // ==================================================================


        // ================= Mendapatkan element id dari xml ================
        btn_add_cart = findViewById(R.id.btn_add_cart);
        btn_back = findViewById(R.id.btn_back);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btnminus = findViewById(R.id.btnminus);
        btnplus = findViewById(R.id.btnplus);
        text_jumlah_ticket = findViewById(R.id.text_jumlah_ticket);
        text_totalharga = findViewById(R.id.text_totalharga);
        text_mybalance = findViewById(R.id.text_mybalance);
        notice_uang = findViewById(R.id.notice_uang);
        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);
        // ===================================================================


        // ========= Mengubah value baru untuk beberapa komponen =============
        text_jumlah_ticket.setText(value_jumlah_ticket.toString());
        // ===================================================================


        // ============ Secara default, hide btnminus ========================
        btnminus.animate().alpha(0).setDuration(300).start();
        btnminus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);
        // ====================================================================


        // ====================== Mengambil data wisata dari firebase berdasarkan intent ================================
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // meminta data yang ada dengan data yang baru
                nama_wisata.setText(snapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(snapshot.child("lokasi").getValue().toString());
                ketentuan.setText(snapshot.child("ketentuan").getValue().toString());
                date_wisata = snapshot.child("date_wisata").getValue().toString();
                time_wisata = snapshot.child("time_wisata").getValue().toString();
                value_hargatiket = Integer.valueOf(snapshot.child("harga_tiket").getValue().toString());

                // Total harga ticket
                value_totalharga = value_jumlah_ticket * value_hargatiket;
                text_totalharga.setText(formatRupiah.format((double)value_totalharga));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // =================================================================================================================


        // ================================== Mengambil data users dari firebase ===========================================
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mybalance = Integer.valueOf(snapshot.child("user_balance").getValue().toString());
                // Uang Saya
                text_mybalance.setText(formatRupiah.format((double)mybalance));
                // Total harga ticket
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // ====================================================================================================================


        // ============================= Button Plus ===========================================
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
        // =======================================================================================


        // ============================ Button Minus =============================================
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
        // ========================================================================================


        // =============================================================================================
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Intent backtoTicketDetail = new Intent(TicketCheckoutAct.this,TicketDetailAct.class);
//                startActivity(backtoTicketDetail);
            }
        });
        // ==============================================================================================


        // ==================================================================================================================================
        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // --------------------------- Mengambil data users kepada firebase dan membuat table baru "My Tickets" -----------------------
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets")
                        .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);

                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(value_jumlah_ticket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gotoSuccessBuyTicket = new Intent(TicketCheckoutAct.this, SuccessBuyTicketAct.class);
                        startActivity(gotoSuccessBuyTicket);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // ----------------------------------------------------------------------------------------------------------------------------------


                // ------------------------------------------------------------------------------------------------------------------------------
                // Update data balance kepada user yang (saat ini sedang login)
                // Mengambil data user dari firebase
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        value_totalharga = value_jumlah_ticket * value_hargatiket;
                        text_totalharga.setText(formatRupiah.format((double)value_totalharga));

                        sisa_balance = mybalance - value_totalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // -------------------------------------------------------------------------------------------------------------------------------

            }
        });
        // ==================================================================================================================================


        // =======================================================================================================================================
        // === ADD CART ====
        btn_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengambil data users kepada firebase dan membuat table baru "My Tickets"
                reference5 = FirebaseDatabase.getInstance().getReference().child("MyTickets_cart")
                        .child(username_key_new).child(nama_wisata.getText().toString());
                reference5.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        reference5.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference5.getRef().child("id_ticket").setValue(nama_wisata.getText().toString());
                        reference5.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference5.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference5.getRef().child("jumlah_tiket").setValue(value_jumlah_ticket.toString());
                        reference5.getRef().child("date_wisata").setValue(date_wisata);
                        reference5.getRef().child("time_wisata").setValue(time_wisata);

                        Toast.makeText(getApplicationContext(), "Berhasil ditambahkan ke cart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //

                // Update data balance kepada user yang (saat ini sedang login)
                // Mengambil data user dari firebase

                reference6 = FirebaseDatabase.getInstance().getReference().child("MyTickets_cart").child(username_key_new).child(nama_wisata.getText().toString());
                reference6.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        value_totalharga = value_jumlah_ticket * value_hargatiket;
                        //text_totalharga.setText(formatRupiah.format((double)value_totalharga));
                        cart_totalharga = value_totalharga;

                        reference6.getRef().child("cart_total_harga").setValue(cart_totalharga);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        // =============================================================================================================================================

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}