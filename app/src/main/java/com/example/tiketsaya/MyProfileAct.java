package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.florent37.shapeofview.shapes.CircleView;

public class MyProfileAct extends AppCompatActivity {

    LinearLayout item_my_ticket;
    Button btn_edit_profile, btn_signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        item_my_ticket = findViewById(R.id.item_my_ticket);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_signout = findViewById(R.id.btn_signout);

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signout = new Intent(MyProfileAct.this,SignAct.class);
                startActivity(signout);
            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoeditprofile = new Intent(MyProfileAct.this,EditProfileAct.class);
                startActivity(gotoeditprofile);
            }
        });

        item_my_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetail = new Intent(MyProfileAct.this,MyTicketDetailAct.class);
                startActivity(gotomyticketdetail);
            }
        });

    }
}