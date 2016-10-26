package com.srmvdp.huddle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Post extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    Button images,post;

    EditText title,summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post1);

        toolbar = (Toolbar) findViewById(R.id.toolbarpost);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Post</font>"));

        images = (Button) findViewById(R.id.selectImages);
        post = (Button) findViewById(R.id.buttonpost);

        title = (EditText) findViewById(R.id.posttitle);



        images.setOnClickListener(this);
        post.setOnClickListener(this);


    }

    public void onClick (View view) {

        if (view == images) {

        }

        if (view == post) {

        }
    }
}
