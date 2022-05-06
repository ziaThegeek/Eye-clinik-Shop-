package com.example.sirmubashar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class listview_adapter extends ArrayAdapter {
    db_handler db_handler;
    TableLayout tableLayout;
    List<String> name, cnic, value1, value2, value3, value4,value5,value6,contact,image_url;
    Activity context;
    TextView name_text,cnic_text,value01, value02, value03, value04,value05,value06,contact_no,expand,edit,delete;
    ImageView photo;
    public listview_adapter(Activity context, List<String> name, List<String> cnic, List<String> value1,
                            List<String> value2, List<String> value3, List<String> value4,List<String> value5,List<String> value6,List<String> contact,List<String> image_url) {
        super(context, R.layout.patient_list, name);
        this.context = context;
        this.name = name;
        this.cnic = cnic;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5=value5;
        this.value6=value6;
        this.contact=contact;
        this.image_url=image_url;

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View item_view = layoutInflater.inflate(R.layout.patient_list, null, true);
        edit=item_view.findViewById(R.id.edit_query);
        delete=item_view.findViewById(R.id.delete);
        name_text = item_view.findViewById(R.id.name);
        cnic_text = item_view.findViewById(R.id.cnic);
        value01 = item_view.findViewById(R.id.v01);
        value02 = item_view.findViewById(R.id.v02);
        value03 = item_view.findViewById(R.id.v03);
        value04 = item_view.findViewById(R.id.v04);
        value05=item_view.findViewById(R.id.v05);
        value06=item_view.findViewById(R.id.v06);
        contact_no = item_view.findViewById(R.id.contact);
        photo = item_view.findViewById(R.id.photo);
        expand=item_view.findViewById(R.id.expand);
        tableLayout=item_view.findViewById(R.id.center_panel);
        name_text.setText(name.get(position));
        cnic_text.setText(cnic.get(position));
        value01.setText(setCommasIntext(value1.get(position)));
        value02.setText(setCommasIntext(value2.get(position)));
        value03.setText(setCommasIntext(value3.get(position)));
        value04.setText(setCommasIntext(value4.get(position)));
        value05.setText(setCommasIntext(value5.get(position)));
        value06.setText(setCommasIntext(value6.get(position)));
        db_handler=new db_handler(context);
        contact_no.setText(contact.get(position));
        if (!image_url.get(position).isEmpty())
        {
            Glide.with(context)
                    .load(image_url.get(position))
                    .into(photo);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,update_details.class);
                intent.putExtra("cnic",cnic.get(position));
                context.startActivity(intent);


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Sure to delete"+name.get(position)+" ?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                db_handler.delete(cnic.get(position));
                                item_view.setVisibility(View.GONE);

                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
        expand.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                RecyclerView.ViewHolder holder= (RecyclerView.ViewHolder) view.getTag();

                if (tableLayout.getVisibility()==View.INVISIBLE||tableLayout.getVisibility()==View.GONE)
                {
                  tableLayout.setVisibility(View.VISIBLE);

                }
                else {

                    tableLayout.setVisibility(View.GONE);
                }
            }
        });
        return item_view;
    }
    public String setCommasIntext(String text)
    {
if (text.length()==3)
   return "D.V , N.V";


        if (text.contains(","))
        {
            text="D.V "+text.substring(0,text.indexOf(","))+" , N.V "+text.substring(text.indexOf(",")+1);
        }
        return text;
    }
}
