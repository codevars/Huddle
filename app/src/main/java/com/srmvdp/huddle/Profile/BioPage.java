package com.srmvdp.huddle.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.srmvdp.huddle.Adapters.BioPostsAdapter;
import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.Fragments.NewsFragment;
import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.News.AppController;
import com.srmvdp.huddle.News.FeedItem;
import com.srmvdp.huddle.R;
import com.srmvdp.huddle.Server.RegisterUserClass;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BioPage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private Button button;

    private ActionBar bar;

    private Button notification;

    private TranslateAnimation slide;

    private String registrationnumber;

    private String savename;

    private String department;

    private String section;

    private TextView name;

    private TextView posts;

    private TextView classsection;

    private SwipeRefreshLayout refresh;

    private SessionManagement session;

    private ListView listView;

    private BioPostsAdapter listAdapter;

    private List<FeedItem> feedItems;

    private Cache cache;

    private Cache.Entry entry;

    private static final String TAG = NewsFragment.class.getSimpleName();

    private String BIO_FEED = "";

    private static final String REQUEST_BIO = "http://codevars.esy.es/getbio.php";

    private static final String REQUEST_FEED = "http://codevars.esy.es/userfeed/userfeed.php";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        session = new SessionManagement(getApplicationContext());

        HashMap<String,String> reg = session.getRegistrationDetails();

        HashMap<String, String> dept = session.getDepartmentDetails();

        HashMap<String, String> sec = session.getSectionDetails();

        HashMap<String, String> saveuser = session.getUserProfileDetails();

        savename = saveuser.get(SessionManagement.FULLNAME);

        registrationnumber = reg.get(SessionManagement.REG_NUM);

        department = dept.get(SessionManagement.DEPARTMENT);

        section = sec.get(SessionManagement.SECTION);

        button = (Button) findViewById(R.id.editbutton);

        button.setOnClickListener(this);

        bar = getSupportActionBar();

        bar.setDisplayHomeAsUpEnabled(true);

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);

        refresh.setOnRefreshListener(this);

        notification = (Button) findViewById(R.id.notification);

        notification.setVisibility(View.GONE);

        name = (TextView) findViewById(R.id.name);

        posts = (TextView) findViewById(R.id.posts);

        classsection = (TextView) findViewById(R.id.classsection);

        name.setText(savename);

        classsection.setText(department + "-" + section);

        listView = (ListView) findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new BioPostsAdapter(this, feedItems);

        listView.setAdapter(listAdapter);

        initialInternetCheck();

        requestFeed(registrationnumber);

        loadCache();

        BIO_FEED = "http://codevars.esy.es/userfeed/" + registrationnumber + ".json";

    }


    public boolean isOnline() {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    private void slide() {

        slide = new TranslateAnimation(0, 0, 500, 0);

        slide.setDuration(1000);

        notification.setAnimation(slide);

    }


    public void initialInternetCheck() {

        if (!isOnline()) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

            notification.setText(getResources().getString(R.string.notconnected));

        }

        else {

            getBio(registrationnumber);

        }

    }


    private void loadCache() {

        cache = AppController.getInstance().getRequestQueue().getCache();

        entry = cache.get(BIO_FEED);

        if (entry != null) {

            try {

                String data = new String(entry.data, "UTF-8");

                try {

                    feedRefresh(new JSONObject(data));

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            }

        } else {

            onRefresh();

        }

    }


    private void refreshResults() {

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, BIO_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                VolleyLog.d(TAG, "Response: " + response.toString());

                if (response != null) {

                    feedRefresh(response);

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jsonReq);

    }


    private void feedRefresh(JSONObject response) {

        try {

            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {

                JSONObject feedObj = (JSONObject) feedArray.get(i);

                final FeedItem item = new FeedItem();

                item.setId(feedObj.getInt("id"));

                item.setProfilePic(feedObj.getString("image"));

                item.setTitle(feedObj.getString("title"));

                item.setTime(feedObj.getString("timestamp"));

                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        feedItems.add(0, item);
                        listAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                    }
                });
            }

            listAdapter.notifyDataSetChanged();

            refresh.setRefreshing(false);

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }


    private void getBio(final String registrationnumber) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                posts.setText("Retrieving Posts");

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s.equalsIgnoreCase("")) {

                    posts.setText("Failed");

                    slide();

                    notification.setVisibility(View.VISIBLE);

                    notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

                    notification.setText(getResources().getString(R.string.networkoverload));

                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            notification.setVisibility(View.GONE);

                        }

                    }, 3000);

                    refresh.setRefreshing(false);

                    return;

                }

                if (s.matches(".*\\d.*")) {

                    posts.setText(s);

                    refresh.setRefreshing(false);

                    return;

                } else {

                    posts.setText("Failed");

                    refresh.setRefreshing(false);

                    Toast.makeText(BioPage.this, s, Toast.LENGTH_LONG).show();

                    return;

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("registrationnumber", params[0]);

                String result = ruc.sendPostRequest(REQUEST_BIO, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();

        ru.execute(registrationnumber);

    }


    private void requestFeed(final String registrationnumber) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s.equalsIgnoreCase("")) {

                    slide();

                    notification.setVisibility(View.VISIBLE);

                    notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

                    notification.setText(getResources().getString(R.string.networkoverload));

                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            notification.setVisibility(View.GONE);

                        }

                    }, 3000);

                    return;

                }

                if (s.contains("Done")) {

                    refreshResults();

                    return;

                } else {

                    slide();

                    notification.setVisibility(View.VISIBLE);

                    notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

                    notification.setText(s);

                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            notification.setVisibility(View.GONE);

                        }

                    }, 3000);

                    return;

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("registrationnumber", params[0]);

                String result = ruc.sendPostRequest(REQUEST_FEED, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();

        ru.execute(registrationnumber);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent intent = new Intent(this, Dashboard.class);

                finish();

                startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onRefresh() {

        if (feedItems != null) {

            feedItems.clear();

            refreshResults();

        }

    }


    @Override
    public void onClick(View view) {

        if (view == button) {

            Intent go = new Intent(BioPage.this, SelfProfile.class);

            finish();

            startActivity(go);

        }

    }

}
