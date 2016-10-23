package com.srmvdp.huddle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SelfProfile extends AppCompatActivity {

    Toolbar toolbar;

    CircleImageView profilephoto;

    TextView changephoto;

    Boolean singlePhoto = true;

    int requestCode;

    int limitPickPhoto = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_profile);

        toolbar = (Toolbar) findViewById(R.id.toolbarselfprofile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Edit Profile</font>"));

        profilephoto = (CircleImageView) findViewById(R.id.profilephoto);
        changephoto = (TextView) findViewById(R.id.changeprofilepictesxt);

        profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(SelfProfile.this,"Profile pic will be selected",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, BioPage.class);
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
