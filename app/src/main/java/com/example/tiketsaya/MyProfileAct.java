package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.github.florent37.shapeofview.shapes.CircleView;

public class MyProfileAct extends AppCompatActivity {

    LinearLayout item_my_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        item_my_ticket = findViewById(R.id.item_my_ticket);

        item_my_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdetail = new Intent(MyProfileAct.this,MyTicketDetailAct.class);
                startActivity(gotomyticketdetail);
            }
        });

    }
}