package com.srmvdp.huddle.AdminPanel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;

import java.util.HashMap;

public class AdminNews extends AppCompatActivity implements View.OnClickListener{

    private ActionBar bar;

    private Button images;

    private Button post;

    private String fullname;

    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_news);


        session = new SessionManagement(getApplicationContext());

        HashMap<String, String> profile = session.getUserProfileDetails();

        fullname = profile.get(SessionManagement.FULLNAME);

        bar = getSupportActionBar();

        bar.setDisplayHomeAsUpEnabled(true);

        images = (Button) findViewById(R.id.selectImages);

        post = (Button) findViewById(R.id.buttonpost);

        images.setOnClickListener(this);

        post.setOnClickListener(this);


    }

    public void onClick (View view) {

        if (view == images) {

        }

        if (view == post) {

        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent intent = new Intent(this, AdminPanel.class);

                finish();

                startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }



}
