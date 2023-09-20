package com.example.slide_7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;

    int requestCode = 1;
    EditText name;
    TextView feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedback = (TextView) findViewById(R.id.feedback);
        name = (EditText) findViewById(R.id.input_name);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    public void sendMessage() {
        String fullName = name.getText().toString();
        String message = "Hello " + fullName + "!";

        Intent intent = new Intent(this, GreetingActivity.class);
        intent.putExtra("fullName", fullName);
        intent.putExtra("message", message);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == this.requestCode) {
//            if (resultCode == Activity.RESULT_OK) {
//                String feedbackMessage = data.getStringExtra("feedback");
//                feedback.setText(feedbackMessage);
//            } else {
//                feedback.setText("No feedback");
//            }
//        }
        //code nay chay lau

        if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
            String feedbackMessage = data.getStringExtra("feedback");
            feedback.setText(feedbackMessage);
        } else {
            feedback.setText("No feedback");
        }
    }
}