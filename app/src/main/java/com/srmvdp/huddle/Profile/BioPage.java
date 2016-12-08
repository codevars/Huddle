package com.srmvdp.huddle.Profile;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.R;

public class BioPage extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    private ActionBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        button = (Button) findViewById(R.id.editbutton);

        button.setOnClickListener(this);

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



    @Override
    public void onClick(View view) {

        if (view == button) {

            Intent go = new Intent(BioPage.this, SelfProfile.class);

            finish();

            startActivity(go);

        }

    }

}
