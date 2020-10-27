package com.example.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    static ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;
    static ArrayList<String> arrayList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);
        Intent intent = new Intent(getApplicationContext(), textBox.class);
        intent.putExtra("index", arrayList.size());
        startActivity(intent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<>();
        arrayList.clear();
        try {
            sharedPreferences = this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
            ;
            arrayList = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (Exception e) {
            e.printStackTrace();
        }
        listView = findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int arrayIndex = i;
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.alert_dark_frame)
                        .setTitle("Do you want to delete this note!!?")
                        .setMessage("this note will be permanently deleted...")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                arrayList.remove(arrayIndex);
                                arrayAdapter.notifyDataSetChanged();
                                try {
                                    SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(arrayList)).apply();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), textBox.class);
                intent.putExtra("value", arrayList.get(i));
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });
    }
}