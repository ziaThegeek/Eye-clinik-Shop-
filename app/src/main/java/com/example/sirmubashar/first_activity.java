package com.example.sirmubashar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class first_activity extends AppCompatActivity {
Button add_patient,patient_details,logout,help;
db_handler db_handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        getSupportActionBar().hide();
        add_patient=findViewById(R.id.add_patient);
        patient_details=findViewById(R.id.patient_details);
        logout=findViewById(R.id.logout);
        help=findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(first_activity.this, help.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_handler=new db_handler(first_activity.this);
                db_handler.set_remember(false,db_handler.getUsername());
                Intent intent1=new Intent(first_activity.this,login.class);
                intent1.putExtra("logout",true);
                startActivity(intent1);
                finish();
            }
        });
        add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(first_activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

     patient_details.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(first_activity.this,patient_details.class);
            startActivity(intent);
        }
    });
}
}