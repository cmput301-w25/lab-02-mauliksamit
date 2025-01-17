package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText cityEditText;

    int selectedPosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //calls parent of this, which is AppCompatActivity
        setContentView(R.layout.activity_main); //tells the activity what it is looking like: figures out which layout file to render



        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cityList = findViewById(R.id.city_list);  //R is a singleton that contains a lot of info about the project
        cityEditText = findViewById(R.id.city_edit_text);
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};
        dataList = new ArrayList<>(); //array list is like a C++ vector: dynamic and nice and easier to work with
        dataList.addAll(Arrays.asList(cities));
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;
            }
        });
        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newCity = cityEditText.getText().toString().trim();
                if (!newCity.isEmpty()){
                    dataList.add(newCity);
                    cityAdapter.notifyDataSetChanged();
                    cityEditText.setText("");
                    Toast.makeText(MainActivity.this, "City added: " + newCity, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition != -1) {
                    String deletedCity = dataList.get(selectedPosition);
                    dataList.remove(selectedPosition);
                    cityAdapter.notifyDataSetChanged();
                    selectedPosition = -1; // Reset selection
                    Toast.makeText(MainActivity.this, "Deleted: " + deletedCity, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Please select a city to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}