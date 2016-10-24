package com.srmvdp.huddle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
    }

    @Override

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this,Dashboard.class);
        startActivity(intent);
    }
}
