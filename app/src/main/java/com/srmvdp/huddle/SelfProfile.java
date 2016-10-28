package com.srmvdp.huddle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;


public class SelfProfile extends AppCompatActivity implements View.OnClickListener {

    private ActionBar bar;

    private CircleImageView profilephoto;

    private TextView changephoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_profile);

        profilephoto = (CircleImageView) findViewById(R.id.profilephoto);

        profilephoto.setOnClickListener(this);

        changephoto = (TextView) findViewById(R.id.changeprofilepictesxt);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent intent = new Intent(this, BioPage.class);

                finish();

                startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }



    @Override
    public void onClick(View view) {

        if (view == profilephoto) {

            Toast.makeText(SelfProfile.this, "Coming Soon!", Toast.LENGTH_SHORT).show();

        }

    }



}
