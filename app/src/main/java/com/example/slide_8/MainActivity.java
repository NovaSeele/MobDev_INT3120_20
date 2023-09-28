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
        EditText userId;
        EditText userName;
        Switch isActive;
        Button send;
        EditText msg;
        Button sendMsg;
        Button sendBrc;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            userId = findViewById(R.id.userId);
            userName = findViewById(R.id.userName);
            isActive = findViewById(R.id.isActive);
            send = findViewById(R.id.send);
            msg = findViewById(R.id.msg);
            sendMsg = findViewById(R.id.sendMsg);
            sendBrc = findViewById(R.id.sendBrc);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    String id = userId.getText().toString();
                    String name = userName.getText().toString();
                    boolean status = isActive.isChecked();

                    intent.putExtra("id", id);
                    intent.putExtra("name", name);
                    intent.putExtra("status", status);

                    startActivity(intent);
                }
            });

            sendMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, msg.getText().toString());
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setComponent(new ComponentName("com.example.slide_8", "com.example.slide_8.ThirdActivity"));
                    startActivity(intent);
                }
            });

            sendBrc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent("com.example.action.MY_SENDER");
                    intent.putExtra(Intent.EXTRA_TEXT, msg.getText().toString());
                    String target = "com.example.slide_8_2";
                    intent.setPackage(target);
                    intent.setComponent(new ComponentName("com.example.slide8_2", "com.example.slide8_2.MyBroadcastReceiver"));
                    sendBroadcast(intent);
                    LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(intent);
                }
            });
        }
    }