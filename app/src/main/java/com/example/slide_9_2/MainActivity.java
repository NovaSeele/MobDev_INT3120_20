package com.example.slide_9_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private static int REQUEST_CODE = 1;
    ImageView picture;
    Button SaveImg;
    OutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SaveImg = findViewById(R.id.saveimgbtn);
        picture = findViewById(R.id.picture);
        SaveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                    //add activity demo function here
                }else {
                    askPermission();
                }
            }
        });
    }

    /* Checks if external storage is available for read and write by clicking a check button*/
    public void check(View view) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Toast.makeText(MainActivity.this, "Yes, it is available for read and write", Toast.LENGTH_SHORT).show();
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Toast.makeText(MainActivity.this, "Yes, it is available for read only", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "No, it is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void askPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveImage();
            }else {
                Toast.makeText(MainActivity.this,"Please provide the required permissions",Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //save current image to external storage
    private void saveImage() {

        File dir = new File(Environment.getExternalStorageDirectory(),"SaveImage");
        if (!dir.exists()){
            dir.mkdir();
        }

        BitmapDrawable drawable = (BitmapDrawable) picture.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File file = new File(dir,System.currentTimeMillis()+".jpg");
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(MainActivity.this,"Successfuly Saved",Toast.LENGTH_SHORT).show();

        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //create new file in external storage
    public void create(View view) {
        File dir = new File(Environment.getExternalStorageDirectory(),"MyFiles");
        if (!dir.exists()){
            dir.mkdir();
        }
        File file = new File(dir,"MyData.txt");

        EditText editText = findViewById(R.id.edit_text);

        String message = editText.getText().toString();
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(message.getBytes());
            Toast.makeText(MainActivity.this,"Successfuly Saved",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"Error Saving",Toast.LENGTH_SHORT).show();
        }
    }
}



//link external storage to emulator: storage/emulated/0