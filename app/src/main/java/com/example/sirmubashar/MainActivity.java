package com.example.sirmubashar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUST_PERMISSION_CODE = 1;
    private static final int REQUST_PERMISSION_CODE_PHONE = 2;
    public static final int PICK_CONTACT    = 1;
    public static final int PICK_IMAGE=2;
    EditText name,CNIC_init,CNIC_middle,CNIC_end,size_left,size_right,contact;
   EditText angle_left,angle_right;
    TextView anl,anr,szl,szr,contact_no;
    Button save,upload_image;
    ImageView photo;
    String image_url;
String Name,cnic,cntact,img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        getSupportActionBar().hide();
        name=findViewById(R.id.name);
        CNIC_init=findViewById(R.id.cnic_init);
        CNIC_middle=findViewById(R.id.cnic_middle);
        CNIC_end=findViewById(R.id.cnic_end);

        save=findViewById(R.id.save_button);

        contact=findViewById(R.id.contact);
        contact_no=findViewById(R.id.contact_no);
        upload_image=findViewById(R.id.upload_image);
        photo=findViewById(R.id.photo);
        image_url="";

upload_image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent( Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);

    }
});
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {

                if (verifyName(name.getText().toString()) && verifyCNICNumber(CNIC_init.getText().toString(), CNIC_middle.getText().
                        toString(), CNIC_end.getText().toString()) && verifyContact(contact.getText().toString())) {


                    Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                    intent.putExtra("name",name.getText().toString());
                    intent.putExtra("cnic",CNIC_init.getText().toString()+"-"+CNIC_middle.getText().toString()+"-"+CNIC_end.getText().toString());
                    intent.putExtra("contact",contact.getText().toString());
                    intent.putExtra("url",image_url);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Missing Values", Toast.LENGTH_SHORT).show();

                }
            }

        });
contact_no.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }
});
    }
    public boolean verifyName(String Name)
    {
        if (Name.isEmpty()) {
            Toast.makeText(MainActivity.this, "invalid name", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(!Name.matches("^[a-zA-Z// //.]+$")){
            Toast.makeText(MainActivity.this, "invalid name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    public boolean verifyCNICNumber(String init,String middle,String end) {
        if (init.isEmpty() || middle.isEmpty() || end.isEmpty()) {
            Toast.makeText(MainActivity.this, "invalid CNIC", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!init.matches("^[0-9]{5}")) {
            Toast.makeText(MainActivity.this, "invalid CNIC", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (!middle.matches("^[0-9]{7}"))

            {
                Toast.makeText(MainActivity.this, "invalid CNIC", Toast.LENGTH_SHORT).show();

                return false;
            }
        else if (!end.matches("^[0-9]{1}")) {
                Toast.makeText(MainActivity.this, "invalid CNIC", Toast.LENGTH_SHORT).show();

                return false;
            } else
                return true;
        }
        public boolean verifyContact(String value)
        {
            if (value.isEmpty())
            {
                return true;
            }
            else if (!value.matches("^[0][3]([0-9]{9})")&&!value.matches("^[+][9][2]([0-9]{10})"))
            {
                Toast.makeText(MainActivity.this, "invalid Contact Number", Toast.LENGTH_SHORT).show();

                return false;
            }
            else
                return true;
        }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                 Uri uri=data.getData();
                   Cursor c = getContentResolver().query(uri, null, null, null, null);
                   int index=c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                   c.moveToFirst();
                   contact.setText(c.getString(index));
                    }
                    break;
            case PICK_IMAGE:
                if (resultCode==Activity.RESULT_OK)
                {
                    image_url=data.getData().toString();
                    Glide.with(this)
                            .load(data.getData())
                            .into(photo);

                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + reqCode);
        }
        }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

        }

        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUST_PERMISSION_CODE);
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
