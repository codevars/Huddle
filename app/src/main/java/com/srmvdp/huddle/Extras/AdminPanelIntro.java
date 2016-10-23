package com.srmvdp.huddle.Extras;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.github.paolorotolo.appintro.AppIntro;
import com.srmvdp.huddle.AdminPanel;
import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;

public class AdminPanelIntro extends AppIntro {

    private SessionManagement session;

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstance(R.layout.intro_one));
        addSlide(SampleSlide.newInstance(R.layout.intro_two));
        addSlide(SampleSlide.newInstance(R.layout.intro_three));
        addSlide(SampleSlide.newInstance(R.layout.intro_four));
        addSlide(SampleSlide.newInstance(R.layout.intro_five));

        session = new SessionManagement(getApplicationContext());

        hideStatusBar();

        setFlowAnimation();

    }



    private void hideStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }

    }


    @Override
    public void onSkipPressed() {

        session.createAdminPanelSession();

        Intent go = new Intent(AdminPanelIntro.this, AdminPanel.class);

        finish();

        startActivity(go);

    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {

        session.createAdminPanelSession();

        Intent go = new Intent(AdminPanelIntro.this, AdminPanel.class);

        finish();

        startActivity(go);

    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v) {
        Toast.makeText(getApplicationContext(), getString(R.string.app_name), Toast.LENGTH_SHORT).show();
    }



}