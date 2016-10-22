package com.srmvdp.huddle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BioPage extends AppCompatActivity {

    Toolbar toolbar;

    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_page);

        toolbar = (Toolbar) findViewById(R.id.toolbarbio);

        button = (Button) findViewById(R.id.buttonedit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(BioPage.this,SelfProfile.class);
                startActivity(in);
            }
        });

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Profile page</font>"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, Dashboard.class);
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
