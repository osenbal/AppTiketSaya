package com.example.tiketsaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;


public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder>{

    Context context;
    ArrayList<MyTicket> myTicket;

    DatabaseReference reference, reference2;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Integer value_price_ticket;

    // generate nomor integer secara random
    // karena kita ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    public  TicketAdapter(Context c, ArrayList<MyTicket> p){
        context = c;
        myTicket = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_ticket_cart, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)  {

        // ======================================================================================
        final MyTicket userData = this.myTicket.get(i);
        final String getNamaWisata = myTicket.get(i).getNama_wisata();
        myViewHolder.xnama_wisata.setText(myTicket.get(i).getNama_wisata());
        myViewHolder.xlokasi.setText(myTicket.get(i).getLokasi());
        myViewHolder.xjumlah_tiket.setText(myTicket.get(i).getJumlah_tiket() + " Tickets");
        // =======================================================================================


        // ============================================================================================
        myViewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ===========================
                getUsernameLocal();
                // ===========================


                // =======================================================================================
                reference = FirebaseDatabase.getInstance().getReference().child("MyTickets_cart");
                Query query = reference.child(username_key_new).child(getNamaWisata);
                // =======================================================================================

                // ========================================================================
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataUser: snapshot.getChildren()) {
                            dataUser.getRef().removeValue();

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // =========================================================================

                Toast.makeText(context, "Delete Success "  , Toast.LENGTH_SHORT).show();

                Intent refrersh = new Intent(context, activity_cart.class);
                context.startActivity(refrersh);
            }


            // =================================================================================================================================
            public void getUsernameLocal(){
                SharedPreferences sharedPreferences = context.getSharedPreferences(USERNAME_KEY, context.MODE_PRIVATE);
                username_key_new = sharedPreferences.getString(username_key, "");
            }
            // =================================================================================================================================
        });

        // ==================================================================================
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetails = new Intent(context, TicketDetailAct.class);
                gotomyticketdetails.putExtra("jenis_tiket", getNamaWisata);

                context.startActivity(gotomyticketdetails);
            }
        });
        // ===================================================================================

    }


    @Override
    public int getItemCount() {
        return myTicket.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_wisata, xlokasi, xjumlah_tiket;
        Button btn_cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // ===========================================================
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_tiket = itemView.findViewById(R.id.xjumlah_tiket);
            // ===========================================================
        }

    }

}
