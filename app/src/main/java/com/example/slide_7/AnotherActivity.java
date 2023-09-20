package com.example.slide_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        TextView textView = findViewById(R.id.another_text);
        Intent intent = this.getIntent();
        textView.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
    }

    public void onBack(View view) {
        onBackPressed();
    }
}