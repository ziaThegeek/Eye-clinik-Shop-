package com.example.sirmubashar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText first_name,last_name,phone,password,re_password,username,login_passowrd,login_username;
    TextView pas_visibilty,re_pas_visibility,login_pass_visibility,register_tab,login_tab;
    Button register,login;
    db_handler db_handler;
    LinearLayout login_layout,register_layout;
    CheckBox remember_me;
    boolean logout;
    TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=findViewById(R.id.register);
        login_passowrd=findViewById(R.id.login_password);
        remember_me=findViewById(R.id.remember_me);
        login_pass_visibility=findViewById(R.id.login_password_visibility);
        pas_visibilty=findViewById(R.id.password_visibility);
        re_pas_visibility=findViewById(R.id.re_password_visibility);
        first_name=findViewById(R.id.first_name);
        username=findViewById(R.id.username);
        last_name=findViewById(R.id.last_name);
        password=findViewById(R.id.password);
        re_password=findViewById(R.id.re_password);
        login=findViewById(R.id.login_button);
        phone=findViewById(R.id.phone_number);
        login_username=findViewById(R.id.login_username);
        login_layout=findViewById(R.id.login_layout);
        register_layout=findViewById(R.id.register_layout);
        login_tab=findViewById(R.id.login_tab);
        register_tab=findViewById(R.id.register_tab);
        forgot_password=findViewById(R.id.forget_password);
        db_handler=new db_handler(this);
        Intent intent=getIntent();
        logout=false;
        logout=intent.getBooleanExtra("logout",false);
//        if (db_handler.get_remember()&&!logout)
//        {
//            Intent intent1=new Intent(login.this,first_activity.class);
//            startActivity(intent1);
//            finish();
//        }
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent1=new Intent(login.this,account_recovery.class);
            startActivity(intent1);
            finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (!db_handler.validate_login())
                    {
                        new AlertDialog.Builder(login.this)
                                .setTitle("Alert")
                                .setMessage("no account exist please register first")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                      show_register_tab();
                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .show();
                    }

                else if (db_handler.find_duplicate(login_username.getText().toString(),login_passowrd.getText().toString()))
                 {
                     Intent intent1=new Intent(login.this,first_activity.class);
                     startActivity(intent1);
                     Toast.makeText(login.this, "login success", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     Toast.makeText(login.this, "invalid username or password", Toast.LENGTH_SHORT).show();
                 }
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if (validate_name(first_name.getText().toString())&&
        validate_name_optional(last_name.getText().toString())
        &&validate_phone(phone.getText().toString())
        &&validate_username(username.getText().toString())
        &&match_password(password.getText().toString(),re_password.getText().toString()))
{
    if (db_handler.validate_login())
    {
        new AlertDialog.Builder(login.this)
                .setTitle("Alert")
                .setMessage("An Account already exists please login")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        show_login_tab();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .show();

    }
    else {
        db_handler.register(first_name.getText().toString() + " " + last_name.getText().toString(),
                phone.getText().toString(), username.getText().toString(), password.getText().toString(),remember_me.isChecked());
        new AlertDialog.Builder(login.this)
                .setTitle("Account Success")
                .setMessage("Account created successfully please login")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        show_login_tab();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

}
else
    Toast.makeText(login.this, "failure", Toast.LENGTH_SHORT).show();

            }
        });
login_pass_visibility.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        change_input_type((TextView) view,login_passowrd);
    }
});
pas_visibilty.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      change_input_type((TextView) view,password);
    }
});
re_pas_visibility.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        change_input_type((TextView) view,re_password);
    }
});
login_tab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      show_login_tab();
    }
});
register_tab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       show_register_tab();
    }
});
    }
    public boolean validate_name(String name)
    {
        if (name.isEmpty())
        {
            Toast.makeText(login.this, "name can not be empty", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (!name.matches("^[a-zA-Z// //.]+$")){
            Toast.makeText(login.this, "invalid name", Toast.LENGTH_SHORT).show();

           return false;
        }
        else
            return true;
    }
    public boolean validate_name_optional(String name){
        if (!name.matches("^[a-zA-Z// //.]+$"))
        {
            Toast.makeText(login.this, "invalid name", Toast.LENGTH_SHORT).show();

            return false;
        }
        else
        return true;

    }
public boolean match_password(String pass,String re_pass)
{
    if (pass.isEmpty())
    {
        Toast.makeText(login.this, "password should'nt be empty", Toast.LENGTH_SHORT).show();

        return false;
    }
    else if (pass.contains(" "))
    {
        Toast.makeText(login.this, "password can't contain spaces ", Toast.LENGTH_SHORT).show();

        return false;
    }
    else if (pass.length()<8)
    {
        Toast.makeText(login.this, "password should contain at least 8 charactors"+pass.length()+re_pass.length(), Toast.LENGTH_SHORT).show();

        return false;
    }

    else if (!pass.equals(re_pass))
    {
        Toast.makeText(login.this, "password does'nt match"+pass.length()+re_pass.length(), Toast.LENGTH_SHORT).show();

        return false;
    }
    else
        return true;
}
    public boolean validate_phone(String phone){
        if (phone.isEmpty())
        {
            return true;
        }
        else if (!phone.matches("^[0][3]([0-9]{9})")&&!phone.matches("^[+][9][2]([0-9]{10})"))
        {
            Toast.makeText(login.this, "invalid Contact Number", Toast.LENGTH_SHORT).show();

            return false;
        }
        else
            return true;
    }
    public boolean validate_username(String username) {
        if (username.isEmpty()) {
            Toast.makeText(login.this, "username shuldn't be empty ", Toast.LENGTH_SHORT).show();

            return false;
        }
        else if (username.contains(" "))
        {
            Toast.makeText(login.this, "username can't contain spaces ", Toast.LENGTH_SHORT).show();

            return false;
        }

        else if (username.length() < 5)

        {
            Toast.makeText(login.this, "username must contain at least 5 chractors", Toast.LENGTH_SHORT).show();

            return false;
        }
        else
        return true;
    }
    public void change_input_type(TextView  icon,EditText view)
    {
        EditText editText= (EditText) view;
        TextView logo= icon;
        if (editText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            logo.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.show_passord,0);
        }
        else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());

            logo.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.hied_password,0);
        }



    }
    public void show_register_tab()
    {
        login_layout.setVisibility(View.GONE);
        register_layout.setVisibility(View.VISIBLE);
        register_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        login_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        register_tab.setTextColor(Color.parseColor("#133C8E"));
        login_tab.setTextColor(Color.parseColor("#75133C8E"));

    }
    public void show_login_tab()
    {
        register_layout.setVisibility(View.GONE);
        login_layout.setVisibility(View.VISIBLE);
        login_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        register_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
        login_tab.setTextColor(Color.parseColor("#133C8E"));
        register_tab.setTextColor(Color.parseColor("#75133C8E"));
    }

}