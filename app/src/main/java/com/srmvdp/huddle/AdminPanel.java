package com.srmvdp.huddle;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.srmvdp.huddle.Adapters.CustomGridViewActivity;

public class AdminPanel extends AppCompatActivity {

    private ActionBar bar;

    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        bar = getSupportActionBar();

        bar.setHomeButtonEnabled(true);

        bar.setDisplayHomeAsUpEnabled(true);


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent back = new Intent(AdminPanel.this, Dashboard.class);

                finish();

                startActivity(back);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    protected void onResume() {

        super.onResume();

        this.doubleBackToExitPressedOnce = false;

    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {

            super.onBackPressed();

            return;

        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please Click Back Again To Exit!", Toast.LENGTH_SHORT).show();

    }



}
