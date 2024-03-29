package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

public class SignAct extends AppCompatActivity {

    TextView btn_new_account;
    Button btn_sign_in;
    EditText xusername, xpassword;
    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        // ==================================================================
        btn_new_account = findViewById(R.id.btn_new_account);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        // ==================================================================


        // =====================================================================================================================================
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ubah state menjadi loading
                btn_sign_in.setEnabled(false);
                btn_sign_in.setText("Loading ...");

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if(username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Kosong !", Toast.LENGTH_SHORT).show();
                    // Ubah state menjadi SIGN IN
                    btn_sign_in.setEnabled(true);
                    btn_sign_in.setText("SIGN IN");
                }
                else {
                    if (password.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Password Kosong !", Toast.LENGTH_SHORT).show();
                        // Ubah state menjadi SIGN IN
                        btn_sign_in.setEnabled(true);
                        btn_sign_in.setText("SIGN IN");
                    } else {
                        if (password.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Password Kosong !", Toast.LENGTH_SHORT).show();
                            // Ubah state menjadi SIGN IN
                            btn_sign_in.setEnabled(true);
                            btn_sign_in.setText("SIGN IN");
                        } else {
                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        //Toast.makeText(getApplicationContext(), "Username Valid :)", Toast.LENGTH_LONG).show();

                                        // Ambil data password dari firebase
                                        String passwordFromFirebase = snapshot.child("password").getValue().toString();

                                        // Validasi password denggan password firebase
                                        if (password.equals(passwordFromFirebase)){

                                            // Menyimpan username (key) pada local (hp)
                                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(username_key, xusername.getText().toString());
                                            editor.apply();

                                            // Berpindah activity
                                            Intent gotohomeact = new Intent(SignAct.this,HomeAct.class);
                                            startActivity(gotohomeact);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "Password Salah !", Toast.LENGTH_LONG).show();
                                            // Ubah state menjadi SIGN IN
                                            btn_sign_in.setEnabled(true);
                                            btn_sign_in.setText("SIGN IN");
                                        }
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Username tidak ditemukan !", Toast.LENGTH_LONG).show();
                                        //Ubah state menjadi SIGN IN
                                        btn_sign_in.setEnabled(true);
                                        btn_sign_in.setText("SIGN IN");
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Database Error !", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            }
        });
        // ================================================================================================================================================


        // =====================================================================================================
        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(SignAct.this,RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
        // ======================================================================================================


    }
}