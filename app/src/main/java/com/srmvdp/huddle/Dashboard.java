package com.srmvdp.huddle;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.srmvdp.huddle.Adapters.ViewPagerAdapter;
import com.srmvdp.huddle.Fragments.NewsFragment;
import com.srmvdp.huddle.Fragments.ShelfFragment;
import com.srmvdp.huddle.Fragments.SubjectsFragment;
import com.srmvdp.huddle.LocalStorage.SessionManagement;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;

    private TabLayout tablayout;

    private ViewPager viewpager;

    private String privilege;

    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new SessionManagement(getApplicationContext());

        HashMap<String, String> right = session.getPrivilegeDetails();

        privilege = right.get(SessionManagement.PRIVILEGE);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        viewpager = (ViewPager) findViewById(R.id.viewpager);

        setUpViewPager(viewpager);

        tablayout = (TabLayout) findViewById(R.id.tablayout);

        tablayout.setupWithViewPager(viewpager);

        TabLayout.Tab tab = tablayout.getTabAt(1);

        tab.select();

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        NavigationDrawer();

    }



    public void setUpViewPager(ViewPager set) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ShelfFragment(), "Shelf");

        adapter.addFragment(new NewsFragment(), "News");

        adapter.addFragment(new SubjectsFragment(), "Subjects");

        set.setAdapter(adapter);

    }



    public void NavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);

        navigationView.setBackgroundColor(getResources().getColor(R.color.WhiteTransparent));

        navigationView.setItemIconTintList(getResources().getColorStateList(R.color.White));

        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){

                    case R.id.profile:

                        Intent in = new Intent(Dashboard.this,BioPage.class);

                        drawerLayout.closeDrawers();
                        startActivity(in);

                        break;


                    case R.id.adminpanel:

                        if (privilege.equals("Admin")) {

                            drawerLayout.closeDrawers();

                            Toast.makeText(getApplicationContext(), "Access Granted!" ,Toast.LENGTH_SHORT).show();

                            session.adminPanel();


                        }

                        else {

                            drawerLayout.closeDrawers();

                            Toast.makeText(getApplicationContext(), "Access Denied!" ,Toast.LENGTH_SHORT).show();

                        }

                        break;


                    case R.id.photos:

                        Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();

                        drawerLayout.closeDrawers();

                        break;


                    case R.id.exit:

                        finish();

                }

                return true;
            }

        });

        View header = navigationView.getHeaderView(0);

        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);

        tv_email.setText("reachrishabh@gmail.com");



        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.draweropen,R.string.drawerclose){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
    }



}

