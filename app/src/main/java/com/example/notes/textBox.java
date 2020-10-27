package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.notes.MainActivity.arrayList;

public class textBox extends AppCompatActivity {
    static EditText editText;
    int index;

    void changetxt(View view) {

        editText = findViewById(R.id.editText);
        String txt = editText.getText().toString();
        if (arrayList.size() <= index) {
            arrayList.add(txt);
        } else if (arrayList.size() != -1) {
            arrayList.set(index, txt);
        }
        MainActivity.arrayAdapter.notifyDataSetChanged();
        try {
            SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(arrayList)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }

    public void update(String value) {
        editText = findViewById(R.id.editText);
        editText.setText(value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        editText = findViewById(R.id.editText);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_box);
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);
        String value = intent.getStringExtra("value");
        update(value);

    }
}
