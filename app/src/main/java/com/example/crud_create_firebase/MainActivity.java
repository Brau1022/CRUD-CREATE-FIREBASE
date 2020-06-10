package com.example.crud_create_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //to put menu icons into the main menu.
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //switch

        switch (item.getItemId()){
            case R.id.icon_add:{
                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            }
            case R.id.icon_save:{
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            }
            case R.id.icon_delete:{
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}