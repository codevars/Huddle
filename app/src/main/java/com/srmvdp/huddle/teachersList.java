package com.srmvdp.huddle;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.srmvdp.huddle.AdminPanel.AdminPanel;
import com.srmvdp.huddle.R;

public class teachersList extends AppCompatActivity {


    private ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);


        bar = getSupportActionBar();

        bar.setHomeButtonEnabled(true);

        bar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent back = new Intent(teachersList.this, Dashboard.class);

                finish();

                startActivity(back);

                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }
}