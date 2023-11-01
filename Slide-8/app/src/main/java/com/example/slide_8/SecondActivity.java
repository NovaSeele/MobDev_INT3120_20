package com.example.slide_8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView user_id;
    TextView user_name;
    TextView status;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        user_id = findViewById(R.id.userId);
        user_name = findViewById(R.id.userName);
        status = findViewById(R.id.status);
        back = findViewById(R.id.back);

        Intent intent = getIntent();
        user_id.setText(intent.getStringExtra("id"));
        user_name.setText(intent.getStringExtra("name"));
        boolean isOnline = intent.getBooleanExtra("status", true);
        String statusText;

        if (isOnline) {
            statusText = "Yes";
        } else {
            statusText = "No";
        }
        status.setText(statusText);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}