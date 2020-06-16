package com.example.basestart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEdit extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        EditText editText = (EditText) findViewById(R.id.editText);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.basestart" , Context.MODE_PRIVATE);

          noteId = getIntent().getIntExtra("ID",-1);

        if (noteId !=-1)
        {
            editText.setText(MainActivity.notes.get(noteId));
        }
        else{
            MainActivity.notes.add("");
            noteId = MainActivity.notes.size()-1;
           // MainActivity.arrayAdapter.notifyDataSetChanged();//chek here
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(s) == "") {
                    noteId -=1;
                }
                else {
                    MainActivity.notes.set(noteId, String.valueOf(s));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    HashSet<String> set = new HashSet<>(MainActivity.notes);
                    sharedPreferences.edit().putStringSet("notes",set).apply();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
