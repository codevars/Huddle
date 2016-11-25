package com.srmvdp.huddle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.srmvdp.huddle.Adapters.ViewPagerAdapter;
import com.srmvdp.huddle.AdminPanel.AdminPosts;
import com.srmvdp.huddle.Extras.ConnectivityReceiver;
import com.srmvdp.huddle.Fragments.NewsFragment;
import com.srmvdp.huddle.Fragments.ShelfFragment;
import com.srmvdp.huddle.Fragments.SubjectsFragment;
import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.News.AppController;
import com.srmvdp.huddle.Server.RegisterUserClass;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String REQUEST_PROFILE = "http://codevars.esy.es/userinfo.php";

    private String fullname;

    private Button notification;

    private Animation slide;

    private DrawerLayout drawerLayout;

    private Toolbar toolbar;

    private TabLayout tablayout;

    private ViewPager viewpager;

    private String privilege;

    private String registration;

    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        session = new SessionManagement(getApplicationContext());

        HashMap<String, String> reg = session.getRegistrationDetails();

        HashMap<String, String> right = session.getPrivilegeDetails();

        privilege = right.get(SessionManagement.PRIVILEGE);

        registration = reg.get(SessionManagement.REG_NUM);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        notification = (Button) findViewById(R.id.notification);

        setSupportActionBar(toolbar);

        viewpager = (ViewPager) findViewById(R.id.viewpager);

        setUpViewPager(viewpager);

        tablayout = (TabLayout) findViewById(R.id.tablayout);

        tablayout.setupWithViewPager(viewpager);

        TabLayout.Tab tab = tablayout.getTabAt(1);

        tab.select();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        NavigationDrawer();

        notification.setVisibility(View.GONE);

        initialInternetCheck();

    }


    public boolean isOnline() {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    public void initialInternetCheck() {

        if (!isOnline()) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

            notification.setText(getResources().getString(R.string.notconnected));

            return;

        }

        else {

            userprofilecheck();

        }

    }


    public void userprofilecheck() {

        if (!session.userProfileIn()) {

            makeuserprofile(registration);

            session.createUserProfileSession();

        }

    }


    private void slide() {

        slide = new TranslateAnimation(0, 0, 500, 0);

        slide.setDuration(1000);

        notification.setAnimation(slide);

    }



    private void generateProfile(String status) {

        if (status.equalsIgnoreCase("initialised")) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationgreen));

            notification.setText(getResources().getString(R.string.generate));

            return;

        }


        if (status.equalsIgnoreCase("processing")) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

            notification.setText(getResources().getString(R.string.processing));

            return;

        }


        if (status.equalsIgnoreCase("done")) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationgreen));

            notification.setText(getResources().getString(R.string.welcome) + " " + fullname + "!");

            return;

        }


    }



    public void makeuserprofile(final String number) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                generateProfile("initialised");

                loading = new ProgressDialog(Dashboard.this, R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equalsIgnoreCase("")) {

                    generateProfile("processing");

                    makeuserprofile(registration);

                } else {

                    fullname = s;

                    session.createUserProfile(fullname);

                    generateProfile("done");

                }


            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();

                data.put("registrationnumber", params[0]);

                String result = ruc.sendPostRequest(REQUEST_PROFILE, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();

        ru.execute(number);

    }


    public void setUpViewPager(ViewPager set) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ShelfFragment(), "Shelf");

        adapter.addFragment(new NewsFragment(), "News");

        adapter.addFragment(new SubjectsFragment(), "Subjects");

        set.setAdapter(adapter);

    }


    public void NavigationDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setBackgroundColor(getResources().getColor(R.color.WhiteTransparent));

        navigationView.setItemIconTintList(getResources().getColorStateList(R.color.White));

        navigationView.setItemTextColor(ColorStateList.valueOf(Color.WHITE));


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {

                    case R.id.profile:

                        Intent in = new Intent(Dashboard.this, BioPage.class);

                        drawerLayout.closeDrawers();

                        startActivity(in);

                        break;


                    case R.id.adminpanel:

                        if (privilege.equals("Admin")) {

                            drawerLayout.closeDrawers();

                            Toast.makeText(getApplicationContext(), "Access Granted!", Toast.LENGTH_SHORT).show();

                            session.adminPanel();


                        } else {

                            drawerLayout.closeDrawers();

                            Toast.makeText(getApplicationContext(), "Access Denied!", Toast.LENGTH_SHORT).show();

                        }

                        break;


                    case R.id.photos:

                        Toast.makeText(getApplicationContext(), "Teacher List", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Dashboard.this, TeacherProfile.class);
                        startActivity(intent);

                        drawerLayout.closeDrawers();

                        break;


                    case R.id.exit:

                        session.logoutUser();

                        Toast.makeText(Dashboard.this, "You Have Logged Out Successfully!", Toast.LENGTH_LONG).show();

                }

                return true;
            }

        });

        View header = navigationView.getHeaderView(0);

        TextView tv_email = (TextView) header.findViewById(R.id.tv_email);

        tv_email.setText("codevars@gmail.com");


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.draweropen, R.string.drawerclose) {

            @Override
            public void onDrawerClosed(View v) {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add:

                startActivity(new Intent(Dashboard.this, AdminPosts.class));

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);

    }


    private void showNotification(boolean isConnected) {

        if (isConnected) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationgreen));

            notification.setText(getResources().getString(R.string.connected));

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    notification.setVisibility(View.GONE);

                }

            }, 3000);

        } else {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

            notification.setText(getResources().getString(R.string.notconnected));

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        AppController.getInstance().setConnectivityListener(this);

    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showNotification(isConnected);

    }


}

