package com.srmvdp.huddle.AdminPanel;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.R;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener {

    private ActionBar bar;

    private ImageView posts;

    private ImageView news;

    private ImageView users;

    private ImageView notifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        posts = (ImageView) findViewById(R.id.adminposts);

        news = (ImageView) findViewById(R.id.adminnews);

        users = (ImageView) findViewById(R.id.adminusers);

        notifications = (ImageView) findViewById(R.id.adminnotifications);

        bar = getSupportActionBar();

        bar.setHomeButtonEnabled(true);

        bar.setDisplayHomeAsUpEnabled(true);

        posts.setOnClickListener(this);

        news.setOnClickListener(this);

        users.setOnClickListener(this);

        notifications.setOnClickListener(this);

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
    public void onClick (View view) {

        if (view == posts) {

            startActivity(new Intent(AdminPanel.this,AdminPosts.class));

        }

        if (view == news) {

            startActivity(new Intent(AdminPanel.this,AdminNews.class));

        }

        if (view == users) {

            startActivity(new Intent(AdminPanel.this,AdminUsers.class));

        }

        if (view == notifications) {

            startActivity(new Intent(AdminPanel.this,AdminNotifications.class));

        }

    }



}
