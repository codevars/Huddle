package com.srmvdp.huddle.Teachers;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.R;

public class TeacherProfile extends AppCompatActivity {

    private ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);


        bar = getSupportActionBar();

        bar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent intent = new Intent(this, Dashboard.class);

                finish();

                startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

}
