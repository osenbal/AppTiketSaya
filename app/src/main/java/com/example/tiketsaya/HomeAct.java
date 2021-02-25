package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

    LinearLayout btn_tiket_pisa;
    CircleView btn_to_profile;
    ImageView photo_home_user;
    TextView nama_lengkap, bio, user_balance;


    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // mengambil data username
        getUsernameLocal();

        btn_tiket_pisa = findViewById(R.id.btn_tiket_pisa);
        btn_to_profile = findViewById(R.id.btn_to_profile);
        photo_home_user = findViewById(R.id.photo_home_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        user_balance = findViewById(R.id.user_balance);
        bio = findViewById(R.id.bio);


        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nama_lengkap.setText(snapshot.child("nama_lengkap").getValue().toString());
                bio.setText(snapshot.child("bio").getValue().toString());
                user_balance.setText("Rp " + snapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this).load(Objects.requireNonNull(snapshot.child("url_photo_profile").getValue()).toString()).centerCrop().fit().into(photo_home_user);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}