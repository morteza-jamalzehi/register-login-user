package com.morivash.newapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView tvUsername, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (!SharedprefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        tvUsername = (TextView) findViewById(R.id.textViewUsername);
        tvEmail = (TextView) findViewById(R.id.textViewEmail);

        tvUsername.setText(SharedprefManager.getInstance(this).getUsername());
        tvEmail.setText(SharedprefManager.getInstance(this).getUserEmali());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            SharedprefManager.getInstance(this).logout();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }
}
