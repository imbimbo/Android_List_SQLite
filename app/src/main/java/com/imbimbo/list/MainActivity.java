package com.imbimbo.list;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;

    Button add_data;
    EditText add_name;

    ListView userlist;

    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();

        add_data = findViewById(R.id.add_data);
        add_name = findViewById(R.id.add_name);
        userlist = findViewById(R.id.users_list);

        viewData();

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = userlist.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

        add_data.setOnClickListener((view) -> {
            String name = add_name.getText().toString();
            if (!name.equals("") && db.insertData(name)) {
                Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                add_name.setText("");
                listItem.clear();
                viewData();
            } else {
                Toast.makeText(MainActivity.this, "Data not added", Toast.LENGTH_SHORT).show();
            }
        });

    }

        private void viewData(){
            Cursor cursor = db.viewData();

            if (cursor.getCount() == 0){
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    listItem.add(cursor.getString(1));
                }
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
                userlist.setAdapter(adapter);
            }
        }
}