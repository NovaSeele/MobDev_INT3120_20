package com.example.slide_10;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Content Provider for Contact Info
    public void getContactButton(View v) {
        getContact();
    }

    @SuppressLint("Range")
    private void getContact() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Request permission
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
        }

        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        assert cursor != null;
        Log.i("CONTACT", "Number of contacts: " + cursor.getCount());
        if (cursor.getCount() >= -1) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Log.i("CONTACT", "Name: " + name);

                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                    while (true) {
                        assert pCur != null;
                        if (!pCur.moveToNext()) break;
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("CONTACT", "Phone number: " + phoneNo);
                    }
                    pCur.close();
                }
            }
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------//

    //Content Provider for Telephony
    private static final int PERMISSION_REQUEST_CODE = 3;

    public void getSmsMmsButton(View v) {
        if (hasReadSmsMmsPermission()) {
            getSmsMmsMessages();
        } else {
            requestReadSmsMmsPermission();
        }
    }

    private boolean hasReadSmsMmsPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadSmsMmsPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.READ_SMS},
                PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("Range")
    private String getRecipientName(ContentResolver contentResolver, String phoneNumber) {
        String recipientName = phoneNumber; // Default to phone number if name not found

        // Query the ContactsContract to get the recipient name
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = contentResolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                recipientName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            }
            cursor.close();
        }

        return recipientName;
    }

    private void getSmsMmsMessages() {
        if (hasReadSmsMmsPermission()) {
            ContentResolver contentResolver = getContentResolver();


            Uri smsUri = Telephony.Sms.CONTENT_URI;
            Cursor smsCursor = contentResolver.query(smsUri, null, null, null, null);

            if (smsCursor != null) {
                while (smsCursor.moveToNext()) {
                    @SuppressLint("Range") String address = smsCursor.getString(smsCursor.getColumnIndex(Telephony.Sms.ADDRESS));
                    @SuppressLint("Range") String body = smsCursor.getString(smsCursor.getColumnIndex(Telephony.Sms.BODY));

                    String recipientName = getRecipientName(contentResolver, address);


                    Log.i("SMS", "Recipient: " + recipientName + ", Address: " + address + ", Body: " + body);
                }
                smsCursor.close();
            }


            Uri mmsUri = Uri.parse("content://mms");
            Cursor mmsCursor = contentResolver.query(mmsUri, null, null, null, null);

            if (mmsCursor != null) {
                while (mmsCursor.moveToNext()) {

                }
                mmsCursor.close();
            }
        } else {
            Toast.makeText(this, "Permission denied. Cannot access SMS/MMS messages.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getSmsMmsMessages();
            } else {

                Toast.makeText(this, "Permission denied. Cannot access SMS/MMS messages.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}




