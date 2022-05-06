package com.example.sirmubashar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class account_recovery extends AppCompatActivity {

    private static final int PERMISSION_SEND_SMS = 1,TIME=16000;
    CountDownTimer countDownTimer;
   LinearLayout verification,changing;
db_handler db_handler;
   Button verify,done;

   static int time=15;
    String OTP,username;
    EditText index0,index1,index2,index3;
    TextView timer_text,resend_otp,verified,username_text,password,re_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_recovery);
        getSupportActionBar().hide();
             time=3;
             timer_text=findViewById(R.id.timer_text);
             db_handler=new db_handler(this);
        index0=findViewById(R.id.index0);
        index1=findViewById(R.id.index1);
        index2=findViewById(R.id.index2);
        index3=findViewById(R.id.index3);
        resend_otp=findViewById(R.id.resend_otp);
        verification=findViewById(R.id.verification);
        changing=findViewById(R.id.changing);
        verify=findViewById(R.id.verfiy);
        verified=findViewById(R.id.verified);
        username_text=findViewById(R.id.username);
        password=findViewById(R.id.password);
        re_password=findViewById(R.id.re_password);
        done=findViewById(R.id.done);
        request_permissions();
        requestSmsPermission();
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        new OTP_reader().setOTPtext(index0,index1,index2,index3);
//        settimer();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((index0.getText().toString()+index1.getText().toString()+index2.getText().toString()+index3.getText().toString()).equals(OTP)) {
                    verification.setVisibility(View.GONE);
                    changing.setVisibility(View.VISIBLE);
                }

                else
                {
                    new AlertDialog.Builder(account_recovery.this)
                            .setTitle("Delete entry")
                            .setMessage("OTP not verified please try again")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation

                                }
                            })
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (validate_username(username_text.getText().toString())&&match_password(password.getText().toString(),re_password.getText().toString())) {
                   db_handler.update_login(db_handler.getUsername(), username_text.getText().toString(), password.getText().toString());
                   Toast.makeText(account_recovery.this, "updated successfully", Toast.LENGTH_SHORT).show();
                   Intent intent1 = new Intent(account_recovery.this, login.class);
                   startActivity(intent1);
                   finish();
               }
            }
        });
    }

    private void request_permissions()

    {
        if (ContextCompat.checkSelfPermission(account_recovery.this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(account_recovery.this,new String[]{Manifest.permission.RECEIVE_SMS},100);
        }
    }
    public void send_sms(){
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        // this will convert any number sequence into 6 character.
        OTP=String.format("%04d", number);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(db_handler.getContact_no(), db_handler.getContact_no(), "Hi user  please don't share it with any one \nYour One time Password(OTP) for account recovery is :"+OTP, null, null);
    }
    private void requestSmsPermission() {

        // check permission is given
        if (ContextCompat.checkSelfPermission(account_recovery.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(account_recovery.this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);
        } else {
            // permission already granted run sms send
            send_sms();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_SEND_SMS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
send_sms();
                } else {
                    // permission denied
                }
                break;
        }
    }

    private void sendSms(String phoneNumber, String message){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
public void settimer()
{
countDownTimer=new CountDownTimer(TIME,1000) {
    @Override
    public void onTick(long l) {

        timer_text.setText(l/1000+" Seconds");
    }

    @Override
    public void onFinish() {
resend_otp.setEnabled(true);
    }
}.start();
}

    public boolean validate_username(String username) {
        if (username.isEmpty()) {
            Toast.makeText(account_recovery.this, "username shouldn't be empty ", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (username.contains(" "))
        {
            Toast.makeText(account_recovery.this, "username can't contain spaces ", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (username.length() < 5)
        {
            Toast.makeText(account_recovery.this, "username must contain at least 5 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }
    public boolean match_password(String pass,String re_pass)
    {
        if (pass.isEmpty())
        {
            Toast.makeText(account_recovery.this, "password should'nt be empty", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (pass.contains(" "))
        {
            Toast.makeText(account_recovery.this, "password can't contain spaces ", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (pass.length()<8)
        {
            Toast.makeText(account_recovery.this, "password should contain at least 8 charactors"+pass.length()+re_pass.length(), Toast.LENGTH_SHORT).show();

            return false;
        }

        else if (!pass.equals(re_pass))
        {
            Toast.makeText(account_recovery.this, "password does'nt match"+pass.length()+re_pass.length(), Toast.LENGTH_SHORT).show();

            return false;
        }
        else
            return true;
    }

}