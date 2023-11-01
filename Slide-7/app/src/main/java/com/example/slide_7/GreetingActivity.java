package com.example.slide_7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GreetingActivity extends AppCompatActivity {

    private Button button;

    TextView message;
    String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        Intent intent = getIntent();
        message = (TextView) findViewById(R.id.mes);
        fullName = intent.getStringExtra("fullName");
        message.setText(intent.getStringExtra("message"));

        button = (Button) findViewById(R.id.back_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backtoMainActivity();
            }
        });
    }

    public void backtoMainActivity() {
        this.onBackPressed();
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        String feedback = "Hello " + fullName + ", this is a response";
        intent.putExtra("feedback", feedback);

        this.setResult(Activity.RESULT_OK, intent);
        super.finish();
    }

}