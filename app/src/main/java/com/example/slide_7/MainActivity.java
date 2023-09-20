package com.example.slide_7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
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


    //implicit intent

    public void onPhone(View view) {
        EditText phoneInput = findViewById(R.id.phoneInput);
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneInput.getText().toString())));
    }

    public void onAnotherActivity(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "When You See It");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setComponent(new ComponentName("com.example.slide_7", "com.example.slide_7.AnotherActivity"));
        startActivity(intent);
    }

    public void onNavMessage(View view) {
        EditText mesInput = findViewById(R.id.messageInput);
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:5551234"));
        intent.putExtra("sms_body", mesInput.getText().toString());
        startActivity(intent);
    }

    public void onImageView(View view) {
        Intent myIntent = new Intent();
        myIntent.setType("image/pictures/*");
        myIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(myIntent);
    }

    public void onEditPhone(View view) {
        Intent intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1"));
        startActivity(intent);
    }

    public void onSeeMap(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California"));
        startActivity(intent);
    }

    public void onOpenPlayer(View view) {
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
        startActivity(intent);
    }

}