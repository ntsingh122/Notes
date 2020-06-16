package com.example.basestart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> notes;
    static ArrayAdapter arrayAdapter ;
    SharedPreferences sharedPreferences;
    String alertMsg;
    int delposn=-1;
    Switch simpleSwitch;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu , menu);
        final MenuItem switchpen = menu.findItem(R.id.app_bar_switch);
        final Switch switcher =     switchpen.getActionView().findViewById(R.id.switch_view);

        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean condn = isChecked;
                if (condn==true){

                    switcher.setText("Encoded");
//                    try {
//                        HashSet<String> set = new HashSet<>(MainActivity.notes);
//                        sharedPreferences.edit().putStringSet(ObjectSerializer.serialize(notes),set).apply();
//                        arrayAdapter.notifyDataSetChanged();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    }
                    else
                    {
                        switcher.setText("Decoded");
                    }
            }
        });
//


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);


        switch (item.getItemId())
        {


                    case  R.id.addn:
                    Intent intent = new Intent(getApplicationContext(),NoteEdit.class);
                    startActivity(intent);

                //Toast.makeText(getApplicationContext(),"Settings", Toast.LENGTH_SHORT).show();
                return true;
                case  R.id.help:
                    Toast.makeText(getApplicationContext(),"what should i help", Toast.LENGTH_SHORT).show();
                    return true;

                case  R.id.about:
                new AlertDialog.Builder(this).setIcon(R.drawable.ic_launcher_foreground)
                        .setTitle("About Me ")
                        .setMessage("Its made with pure Joblessness. \nI even don't want you to use it.").show();
                return true;

                case  R.id.exit:
                exits("exit ?");
                return true;
                default:
                    return false;

        }

    }

    public void exits(String msg)
    {
        alertMsg = msg;
        new AlertDialog.Builder(this).setIcon(R.drawable.audo)
                .setTitle("Warning !")
                .setMessage("Are You sure you want to "+msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (alertMsg == "exit ?") //used for exiting
                        {
                            finish();
                        }
                        else //used for deleting a note
                        {
                            notes.remove(delposn);
                            arrayAdapter.notifyDataSetChanged();// to bring changes in list
                            HashSet<String> set = new HashSet<>(MainActivity.notes);  //to bring changes in the shared preferences
                            sharedPreferences.edit().putStringSet("notes",set).apply();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.basestart" , Context.MODE_PRIVATE);
        listView = (ListView) findViewById(R.id.listView);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);
        notes = new ArrayList<>();
        if (set == null)
            notes.add("put ur notes here !");
        else
            notes = new ArrayList(set);
        arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),NoteEdit.class);
                intent.putExtra("ID",position);
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delposn = position;
                exits("delete note ?");
                return true;
            }
        });
    }
}
