package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


public class RegisterTwoAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue_reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        btn_back = findViewById(R.id.btn_back);
        btn_continue_reg  = findViewById(R.id.btn_continue_reg);

        btn_continue_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent continuetosuccessact = new Intent(RegisterTwoAct.this,SuccessRegisterAct.class);
                startActivity(continuetosuccessact);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoprev = new Intent(RegisterTwoAct.this,RegisterOneAct.class);
                startActivity(backtoprev);
            }
        });
    }
}