package com.example.sirmubashar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class patient_details extends AppCompatActivity {
AutoCompleteTextView search_text;
ListView listView;
TextView sorting_options,search,refresh;
List<String> name1,cnic1,anl1,anr1,szl1,szr1,xzl,xzr,contact1,image_url1;
    ArrayAdapter<String> cnicAdapte,nameAdapte;
    listview_adapter listview_adapter;
    List<String> name,cnic,value1,value2,value3,value4,value5,value6,contact,image_url;
    db_handler db_handler;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        getSupportActionBar().hide();
        listView=findViewById(R.id.list_view);
        search_text=findViewById(R.id.search_text);
        refresh=findViewById(R.id.refresh);
        search=findViewById(R.id.search_button);
db_handler=new db_handler(this);
        db_handler.load_data();
        init_data();
        name = db_handler.getNames();
        cnic = db_handler.getCnic();
        value1 = db_handler.getValue1();
        value2 = db_handler.getValue2();
        value3 = db_handler.getValue3();
        value4 = db_handler.getValue4();
        value5 = db_handler.getValue5();
        value6 = db_handler.getValue6();
        contact=db_handler.getContact();
        image_url=db_handler.getImage_url();
        cnic1=new ArrayList<>();
        name1=new ArrayList<>();
        anl1=new ArrayList<>();
        anr1=new ArrayList<>();
        szl1=new ArrayList<>();
        szr1=new ArrayList<>();
        xzl=new ArrayList<>();
        xzr=new ArrayList<>();
        image_url1=new ArrayList<>();
        contact1=new ArrayList<>();
        cnicAdapte=new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,cnic);
        nameAdapte=new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,name);

        search_text.setAdapter(cnicAdapte);
        listview_adapter=new listview_adapter(patient_details.this,name,cnic,value1,value2,value3,value4,value5,value6,contact,image_url);
        listView.setAdapter(listview_adapter);
        sorting_options=findViewById(R.id.sort);
        sorting_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(patient_details.this, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.sorting_options, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.CNIC:
                                search_text.setAdapter(cnicAdapte);
                                break;
                            case R.id.name:
                                search_text.setAdapter(nameAdapte);
                                break;
                        }
                        return true;
                    }
                });
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
                listview_adapter=null;
                listview_adapter=new listview_adapter(patient_details.this,name,cnic,value1,value2,value3,value4,value5,value6,contact,image_url);
                listView.setAdapter(listview_adapter);
            }

        });
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Cursor cursor=db_handler.search(search_text.getText().toString());
                set_search_data(cursor);
                listview_adapter=null;
                listview_adapter=new listview_adapter(patient_details.this,name,cnic,value1,value2,value3,value4,value5,value6,contact,image_url);
                listView.setAdapter(listview_adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cleanData();
//                Cursor cursor=db_handler.search(search_text.getText().toString());
//                set_search_data(cursor);
//                listview_adapter listview_adapter=new listview_adapter(patient_details.this,name1,cnic1,anl1,anr1,szl1,szr1,xzl,xzr,contact1,image_url1);
//                listView.setAdapter(listview_adapter);
            }
        });

        search_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    public void set_search_data(Cursor cursor)
    {
        clear_data();
        while (cursor.moveToNext())
        {
            name.add(cursor.getString(0));
            cnic.add(cursor.getString(1));
            contact.add(cursor.getString(2));
            image_url.add(cursor.getString(3));
            value1.add(cursor.getString(4));
            value2.add(cursor.getString(5));
            value3.add(cursor.getString(6));
            value4.add(cursor.getString(7));
            value5.add(cursor.getString(8));
           value6.add(cursor.getString(9));
        }
        cursor.close();
    }
    public void cleanData()
    {
        name1.clear();
        cnic1.clear();
        contact1.clear();
        anr1.clear();
        anl1.clear();
        szr1.clear();
        szl1.clear();
        xzl.clear();
        xzr.clear();
        image_url1.clear();
    }
    public void init_data()
    {
        name=new ArrayList<>();
        cnic=new ArrayList<>();
        contact=new ArrayList<>();
        image_url=new ArrayList<>();
        value1=new ArrayList<>();
        value2=new ArrayList<>();
        value3=new ArrayList<>();
        value4=new ArrayList<>();
        value5=new ArrayList<>();
        value6=new ArrayList<>();


    }
    public  void refresh()
    {

db_handler=null;
db_handler=new db_handler(this);
db_handler.load_data();
         name = db_handler.getNames();
         cnic = db_handler.getCnic();
      value1 = db_handler.getValue1();
       value2 = db_handler.getValue2();
         value3 = db_handler.getValue3();
        value4 = db_handler.getValue4();
        value5 = db_handler.getValue5();
         value6 = db_handler.getValue6();
       contact=db_handler.getContact();
        image_url=db_handler.getImage_url();
    }
    public void clear_data()
    {
        name.clear();;
        cnic.clear();
        contact.clear();
        value1.clear();
        value2.clear();
        value3.clear();
        value4.clear();
        value5.clear();
        value6.clear();
    }

}