package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.net.CookieHandler;
import java.util.ArrayList;

public class activity_cart extends AppCompatActivity {

    LinearLayout item_my_ticket_cart, btn_back;
    Button btn_cancel, btn_buy_ticket;

    DatabaseReference reference, reference2, reference3;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    RecyclerView myticket_cart;
    ArrayList<MyTicket> list;
    TicketAdapter ticketAdapter;

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
        // ====================================================================


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
                Toast.makeText(getApplicationContext(), "Maaf Masih dalam perbaikan !", Toast.LENGTH_SHORT).show();

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