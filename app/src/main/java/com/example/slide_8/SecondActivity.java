package com.example.slide_8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView id;
    TextView name;
    TextView status;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        id = findViewById(R.id.userId);
        name = findViewById(R.id.userName);
        status = findViewById(R.id.status);
        back = findViewById(R.id.back);

        Intent intent = getIntent();
        id.setText(intent.getStringExtra("id"));
        name.setText(intent.getStringExtra("name"));
        status.setText(intent.getBooleanExtra("status", true) ? "Online" : "Offline");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}