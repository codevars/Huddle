package com.srmvdp.huddle.Authentication;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.srmvdp.huddle.Adapters.ViewPagerAdapter;
import com.srmvdp.huddle.Authentication.LoginFragment;
import com.srmvdp.huddle.Authentication.RegisterFragment;
import com.srmvdp.huddle.R;

public class LoginRegisterTabbed extends AppCompatActivity {

    private ViewPager viewpager;

    private TabLayout tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_tabbed);

        // Initializing Viewpager

        viewpager = (ViewPager) findViewById(R.id.viewpager);

        setViewPager(viewpager);

        // Initializing TabLayout

        tablayout = (TabLayout) findViewById(R.id.tablayout);

        tablayout.setupWithViewPager(viewpager);


        // Hiding Status Bar

        hideStatusBar();

    }



    private void setViewPager(ViewPager set) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new LoginFragment(), "SIGN IN");

        adapter.addFragment(new RegisterFragment(), "SIGN UP");

        set.setAdapter(adapter);

    }



    private void hideStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        }

    }


}
