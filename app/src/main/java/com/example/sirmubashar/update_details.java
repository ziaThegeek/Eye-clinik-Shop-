package com.example.sirmubashar;

import static com.example.sirmubashar.MainActivity.PICK_IMAGE;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class update_details extends AppCompatActivity {
    ImageView imageView;
    String image_url;
    EditText name,CNIC_init,CNIC_middle,CNIC_end,size_left,size_right,contact;
    Button save,upload;
    TextView contact_no;
    TextView sign00,sign01,sign02,sign10,sign11,sign12,resign00,resign01,resign02,resign10,resign11,resign12,table_l,table_r;
    EditText value00,value01,value02,value10,value11,value12,revalue00,revalue01,revalue02,revalue10,revalue11,revalue12;
    TableLayout left,right;
    public static final int PICK_CONTACT    = 1;
    private static final int REQUST_PERMISSION_CODE = 1;
    db_handler db_handler;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);
        getSupportActionBar().setTitle("Update Person Details ");
        Intent intent=getIntent();
        db_handler=new db_handler(this);
        image_url="";
        name=findViewById(R.id.name);
        CNIC_init=findViewById(R.id.cnic_init);
        CNIC_middle=findViewById(R.id.cnic_middle);
        CNIC_end=findViewById(R.id.cnic_end);
        imageView=findViewById(R.id.photo);
        upload=findViewById(R.id.upload_image);
        save=findViewById(R.id.save_button);
        contact=findViewById(R.id.contact);
        contact_no=findViewById(R.id.contact_no);
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
        save=findViewById(R.id.save_button);
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

        load_data(db_handler.search_fix(intent.getStringExtra("cnic")));




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
                        verifyNumber(revalue12.getText().toString())&&
                        verifyName(name.getText().toString()) &&
                        verifyCNICNumber(CNIC_init.getText().toString(), CNIC_middle.getText().
                        toString(), CNIC_end.getText().toString()) &&
                        verifyContact(contact.getText().toString())
                )
                {
//                    Toast.makeText(update_details.this, "done+\n"+data, Toast.LENGTH_SHORT).show();



                }
                else
                {
                    Toast.makeText(update_details.this, "missing or invalid values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
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







        contact_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
db_handler.update(name.getText().toString(),
        CNIC_init.getText().toString()+"-"+CNIC_middle.getText().toString()+"-"+CNIC_end.getText().toString(),
        contact.getText().toString(),image_url,
        sign00.getText().toString()+value00.getText().toString()+","+sign10.getText().toString()+value10.getText().toString(),
        sign01.getText().toString()+value01.getText().toString()+","+sign11.getText().toString()+value11.getText().toString(),
        sign02.getText().toString()+value02.getText().toString()+","+sign12.getText().toString()+value12.getText().toString(),
        resign00.getText().toString()+revalue00.getText().toString()+","+resign10.getText().toString()+revalue10.getText().toString(),
        resign01.getText().toString()+revalue01.getText().toString()+","+resign01.getText().toString()+revalue11.getText().toString(),
        resign02.getText().toString()+revalue02.getText().toString()+","+resign12.getText().toString()+revalue12.getText().toString());
                new AlertDialog.Builder(update_details.this)
                        .setTitle("Alert")
                        .setMessage("updated successfully")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                Intent intent1=new Intent(update_details.this,first_activity.class);
                                startActivity(intent1);
                            }
                        }).show();
            }

        });


    }
    public boolean verifyName(String Name)
    {
        if (Name.isEmpty()) {
            Toast.makeText(update_details.this, "invalid name", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if(!Name.matches("^[a-zA-Z// //.]+$")){
            Toast.makeText(update_details.this, "invalid name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    public boolean verifyCNICNumber(String init,String middle,String end) {
        if (init.isEmpty() || middle.isEmpty() || end.isEmpty()) {
            Toast.makeText(update_details.this, "invalid CNIC", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!init.matches("^[0-9]{5}")) {
            Toast.makeText(update_details.this, "invalid CNIC", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (!middle.matches("^[0-9]{7}"))

        {
            Toast.makeText(update_details.this, "invalid CNIC", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (!end.matches("^[0-9]{1}")) {
            Toast.makeText(update_details.this, "invalid CNIC", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(update_details.this, "invalid Contact Number", Toast.LENGTH_SHORT).show();

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
                            .into(imageView);

                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + reqCode);
        }
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
    public void load_data(Cursor cursor)
    {
        cursor.moveToFirst();
        name.setText(cursor.getString(0));
        CNIC_init.setText(cursor.getString(1).substring(0,cursor.getString(1).indexOf("-")));
        CNIC_middle.setText(cursor.getString(1).substring(cursor.getString(1).indexOf("-")+1,cursor.getString(1).lastIndexOf("-")));
        CNIC_end.setText(cursor.getString(1).substring(cursor.getString(1).lastIndexOf("-")+1));
        contact.setText(cursor.getString(2));

        sign00.setText(cursor.getString(4).substring(0,1));
        sign10.setText(cursor.getString(4).substring(cursor.getString(4).indexOf(",")+1,cursor.getString(4).indexOf(",")+2));
        value00.setText(cursor.getString(4).substring(1,cursor.getString(4).indexOf(",")));
        value10.setText(cursor.getString(4).substring(cursor.getString(4).indexOf(",")+2));



        sign01.setText(cursor.getString(5).substring(0,1));
        sign11.setText(cursor.getString(5).substring(cursor.getString(5).indexOf(",")+1,cursor.getString(5).indexOf(",")+2));
        value01.setText(cursor.getString(5).substring(1,cursor.getString(5).indexOf(",")));
        value11.setText(cursor.getString(5).substring(cursor.getString(5).indexOf(",")+2));

        sign02.setText(cursor.getString(6).substring(0,1));
        sign12.setText(cursor.getString(6).substring(cursor.getString(6).indexOf(",")+1,cursor.getString(6).indexOf(",")+2));
        value02.setText(cursor.getString(6).substring(1,cursor.getString(6).indexOf(",")));
        value12.setText(cursor.getString(6).substring(cursor.getString(6).indexOf(",")+2));


        resign00.setText(cursor.getString(7).substring(0,1));
        resign10.setText(cursor.getString(7).substring(cursor.getString(7).indexOf(",")+1,cursor.getString(7).indexOf(",")+2));
        revalue00.setText(cursor.getString(7).substring(1,cursor.getString(7).indexOf(",")));
        revalue10.setText(cursor.getString(7).substring(cursor.getString(7).indexOf(",")+2));


        resign01.setText(cursor.getString(8).substring(0,1));
        resign11.setText(cursor.getString(8).substring(cursor.getString(8).indexOf(",")+1,cursor.getString(8).indexOf(",")+2));
        revalue01.setText(cursor.getString(8).substring(1,cursor.getString(8).indexOf(",")));
        revalue11.setText(cursor.getString(8).substring(cursor.getString(8).indexOf(",")+2));


        resign02.setText(cursor.getString(9).substring(0,1));
        resign12.setText(cursor.getString(9).substring(cursor.getString(9).indexOf(",")+1,cursor.getString(9).indexOf(",")+2));
        revalue02.setText(cursor.getString(9).substring(1,cursor.getString(9).indexOf(",")));
        revalue12.setText(cursor.getString(9).substring(cursor.getString(9).indexOf(",")+2));

    }

}