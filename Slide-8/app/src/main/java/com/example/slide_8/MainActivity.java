    package com.example.slide_8;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.localbroadcastmanager.content.LocalBroadcastManager;

    import android.content.ComponentName;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Switch;

    public class MainActivity extends AppCompatActivity {
        EditText user_id;
        EditText user_name;
        EditText message;
        Switch status;
        Button send;
        Button send_intent;
        Button send_boardcast;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            user_id = findViewById(R.id.userId);
            user_name = findViewById(R.id.userName);
            status = findViewById(R.id.isActive);
            send = findViewById(R.id.send);
            message = findViewById(R.id.message);
            send_intent = findViewById(R.id.send_intent);
            send_boardcast = findViewById(R.id.send_boardcast);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    String id = user_id.getText().toString();
                    String name = user_name.getText().toString();
                    boolean status = MainActivity.this.status.isChecked();

                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("status", status);

                    startActivity(intent);
                }
            });

            send_intent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setComponent(new ComponentName("com.example.slide_8", "com.example.slide_8.ThirdActivity"));
                    startActivity(intent);
                }
            });

            send_boardcast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.action.MY_SENDER");
                    intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
                    String target = "com.example.slide_8_2";
                    intent.setPackage(target);
                    intent.setComponent(new ComponentName("com.example.slide8_2", "com.example.slide8_2.MyBroadcastReceiver"));
                    sendBroadcast(intent);
                    LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                }
            });
        }
    }