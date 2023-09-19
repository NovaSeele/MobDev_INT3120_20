package com.example.slide_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.text_view_context_menu);
        registerForContextMenu(textView);
    }

    // Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.item1){
            Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.item2){
            Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.item3){
            Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.subitem1) {
            Toast.makeText(this, "Sub Item 1", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.subitem2) {
            Toast.makeText(this, "Sub Item 2", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    // Popup Menu
    public void showPopup(android.view.View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId()==R.id.item1){
            Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show();
        } else if (menuItem.getItemId()==R.id.item2){
            Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
        } else if (menuItem.getItemId()==R.id.item3){
            Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show();
        } else if (menuItem.getItemId()==R.id.item4) {
            Toast.makeText(this, "Item 4", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    // Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.option_1){
            Toast.makeText(this, "Option 1 selected", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId()==R.id.option_2){
            Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}