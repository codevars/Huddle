package com.srmvdp.huddle.Teachers;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.R;

public class TeacherList extends AppCompatActivity {

    TextView title;
    Typeface tf;
    EditText search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);

        title = (TextView) findViewById(R.id.tListTitle);

        search = (EditText) findViewById(R.id.tListEdit1);

        tf = Typeface.createFromAsset(getAssets(),"fonts/Satellite.otf");






        title.setTypeface(tf);

        search.setTypeface(tf);

    }


}