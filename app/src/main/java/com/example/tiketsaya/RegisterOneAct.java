package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;

public class RegisterOneAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;
    EditText username, password, email_address;

    DatabaseReference reference, reference_username, reference_data;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        // Meload Element berdasarkan ID
        btn_back = findViewById(R.id.btn_back);
        btn_continue  = findViewById(R.id.btn_continue);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Ausername = username.getText().toString();
                final String Apassword = password.getText().toString();
                final String Aemail_address = email_address.getText().toString();

                //Ubah state menjadi loading
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading ...");

                if (Ausername.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Require !", Toast.LENGTH_SHORT).show();
                    //Ubah state menjadi loading
                    btn_continue.setEnabled(true);
                    btn_continue.setText("SIGN IN");
                }
                else {
                    if(Apassword.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Require !", Toast.LENGTH_SHORT).show();
                        //Ubah state menjadi loading
                        btn_continue.setEnabled(true);
                        btn_continue.setText("SIGN IN");
                    } else {
                        if (Aemail_address.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Email Require !", Toast.LENGTH_SHORT).show();
                            //Ubah state menjadi loading
                            btn_continue.setEnabled(true);
                            btn_continue.setText("SIGN IN");
                        } else {
                            if (Aemail_address.isEmpty()){
                                Toast.makeText(getApplicationContext(), "Email Require !", Toast.LENGTH_SHORT).show();
                                //Ubah state menjadi loading
                                btn_continue.setEnabled(true);
                                btn_continue.setText("SIGN IN");
                            } else {
                                // mengambil username pada database
                                reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                                reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()){
                                            Toast.makeText(getApplicationContext(), "Username Sudah tersedia !", Toast.LENGTH_SHORT).show();

                                            //Ubah state menjadi active
                                            btn_continue.setEnabled(true);
                                            btn_continue.setText("CONTINUE");

                                        }
                                        else {
                                            // menyimpan data pada local storage (hp)
                                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(username_key, username.getText().toString());
                                            editor.apply();

                                            //Toast.makeText(getApplicationContext(), "Username " + username.getText().toString(), Toast.LENGTH_SHORT).show();

                                            //Menyimpan ke database
                                            reference = FirebaseDatabase.getInstance()
                                                    .getReference().child("Users").child(username.getText().toString());
                                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                                    dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                                    dataSnapshot.getRef().child("email_address").setValue(email_address.getText().toString());
                                                    dataSnapshot.getRef().child("user_balance").setValue(0);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                            // berpindah ke act selanjutnya
                                            Intent gotoregistertwo = new Intent(RegisterOneAct.this,RegisterTwoAct.class);
                                            startActivity(gotoregistertwo);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getApplicationContext(), "Warning Database Error !", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }


                        }
                    }

                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Intent backtosignin = new Intent(RegisterOneAct.this,SignAct.class);
//                startActivity(backtosignin);
            }
        });

    }

}