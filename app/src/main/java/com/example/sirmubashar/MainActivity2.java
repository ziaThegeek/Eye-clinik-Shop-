package com.example.sirmubashar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    TextView sign00,sign01,sign02,sign10,sign11,sign12,resign00,resign01,resign02,resign10,resign11,resign12,table_l,table_r;
    EditText value00,value01,value02,value10,value11,value12,revalue00,revalue01,revalue02,revalue10,revalue11,revalue12;
    TableLayout left,right;
Button save;
List<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        data=new ArrayList<>();
        Intent intent=getIntent();
        data.add(intent.getStringExtra("name"));
        data.add(intent.getStringExtra("cnic"));
        data.add(intent.getStringExtra("contact"));
        data.add(intent.getStringExtra("url"));
        getSupportActionBar().hide();
        table_l=findViewById(R.id.left_table);
        table_r=findViewById(R.id.right_table);
        left=findViewById(R.id.table_left);
        right=findViewById(R.id.table_right);
        sign00=findViewById(R.id.sign00);
        sign01=findViewById(R.id.sign01);
        sign02=findViewById(R.id.sign02);

        sign10=findViewById(R.id.sign10);
        sign11=findViewById(R.id.sign11);
        sign12=findViewById(R.id.sign12);

        resign00=findViewById(R.id.resign00);
        resign01=findViewById(R.id.resign01);
        resign02=findViewById(R.id.resign02);

        resign10=findViewById(R.id.resign10);
        resign11=findViewById(R.id.resign11);
        resign12=findViewById(R.id.resign12);


        value00=findViewById(R.id.value00);
        value01=findViewById(R.id.value01);
        value02=findViewById(R.id.value02);

    value10=findViewById(R.id.value10);
    value11=findViewById(R.id.value11);
    value12=findViewById(R.id.value12);

    revalue00=findViewById(R.id.revalue00);
    revalue01=findViewById(R.id.revalue01);
    revalue02=findViewById(R.id.revalue02);

    revalue10=findViewById(R.id.revalue10);
    revalue11=findViewById(R.id.revalue11);
    revalue12=findViewById(R.id.revalue12);
  
    save=findViewById(R.id.save_button);

    save.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (verifyNumber(value00.getText().toString())&&
                    verifyNumber(value00.getText().toString())&&
                    verifyNumber(value01.getText().toString())&&
                    verifyNumber(value02.getText().toString())&&
                    verifyNumber(value10.getText().toString())&&
                    verifyNumber(value11.getText().toString())&&
                    verifyNumber(value12.getText().toString())&&
                    verifyNumber(revalue00.getText().toString())&&
                    verifyNumber(revalue01.getText().toString())&&   
                    verifyNumber(revalue02.getText().toString())&&
                    verifyNumber(revalue10.getText().toString())&&
                    verifyNumber(revalue11.getText().toString())&&
                    verifyNumber(revalue12.getText().toString())
            )
            {
//                Toast.makeText(MainActivity2.this, "done+\n"+data, Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(MainActivity2.this)
                        .setTitle("Alert")
                        .setMessage("details added successfully")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                save_data();
                                // Continue with delete operation
                              Intent intent1=new Intent(MainActivity2.this,first_activity.class);
                              startActivity(intent1);
                              finish();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .show();



            }
            else
            {
                Toast.makeText(MainActivity2.this, "missing or invalid values", Toast.LENGTH_SHORT).show();
            }
        }
    });
table_l.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        right.setVisibility(View.GONE);
        left.setVisibility(View.VISIBLE);
        table_l.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        table_r.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        table_l.setTextColor(Color.parseColor("#133C8E"));
        table_r.setTextColor(Color.parseColor("#75133C8E"));




    }
});
table_r.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        left.setVisibility(View.GONE);
        right.setVisibility(View.VISIBLE);
        table_r.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        table_l.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        table_r.setTextColor(Color.parseColor("#133C8E"));
        table_l.setTextColor(Color.parseColor("#75133C8E"));

    }
});

View.OnClickListener signClick=new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        chage_value(view);

    }
};

        sign00.setOnClickListener(signClick);
        sign01.setOnClickListener(signClick);
        sign02.setOnClickListener(signClick);
        sign10.setOnClickListener(signClick);
        sign11.setOnClickListener(signClick);
        sign12.setOnClickListener(signClick);
        resign00.setOnClickListener(signClick);
        resign01.setOnClickListener(signClick);
        resign02.setOnClickListener(signClick);
        resign10.setOnClickListener(signClick);
        resign11.setOnClickListener(signClick);
        resign12.setOnClickListener(signClick);

    }
public void chage_value(View view)
{
    TextView textView= (TextView) view;
    if (textView.getText()=="+")
        textView.setText("-");
    else
        textView.setText("+");
}
public boolean verifyNumber(String value){
        if(value.isEmpty())
            return true;
else if (value.matches("^([0-9]+\\.?[0-9]*|\\.[0-9]+)$"))
    return true;
else
    return false;
}
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void  save_data()
    {
        db_handler db_handler=new db_handler(this);
        db_handler.insert_data(data.get(0),data.get(1)
                ,data.get(2),data.get(3),
                sign00.getText().toString()+value00.getText().toString()+","+sign10.getText().toString()+value10.getText().toString(),

                sign01.getText().toString()+value01.getText().toString()+","+sign11.getText().toString()+value11.getText().toString(),

                sign02.getText().toString()+value02.getText().toString()+","+sign12.getText().toString()+value12.getText().toString(),

                resign00.getText().toString()+revalue00.getText().toString()+","+resign10.getText().toString()+revalue10.getText().toString(),

                resign01.getText().toString()+revalue01.getText().toString()+","+resign11.getText().toString()+revalue11.getText().toString(),

                resign02.getText().toString()+revalue02.getText().toString()+","+resign12.getText().toString()+revalue12.getText().toString()
        );
    }
}