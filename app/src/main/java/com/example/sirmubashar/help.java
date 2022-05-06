package com.example.sirmubashar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class help extends AppCompatActivity {
    private static final int REQUST_PERMISSION_CODE = 1;
    TextView phone1,phone2,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        phone1=findViewById(R.id.phone1);
        phone2=findViewById(R.id.phone2);
        mail=findViewById(R.id.mail);
        requestPermission();

        phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:03414657341"));
                startActivity(intent);
            }
        });
        phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:03037842297"));
                startActivity(intent);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setData(Uri.parse("mailto:")); // only email apps should handle this
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "infinite.studios.support@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Farooqi Otptics query");
                email.putExtra(Intent.EXTRA_TEXT, "write your issue here");

//need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE))
        {

        }

        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CALL_PHONE},REQUST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUST_PERMISSION_CODE&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
            }
            else {
                Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }

}