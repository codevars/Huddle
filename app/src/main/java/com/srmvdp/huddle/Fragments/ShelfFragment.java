package com.srmvdp.huddle.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.srmvdp.huddle.Adapters.FeedClassPostsAdapter;
import com.srmvdp.huddle.Dashboard;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShelfFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public ShelfFragment() {
    }

    private static final String TAG = NewsFragment.class.getSimpleName();

    private int offlinefeedlength;

    private int onlinefeedlength;

    private int posts;

    private String registrationnumber;

    private String department;

    private String section;

    private String selected;

    private Spinner sectionspinner;

    private Cache cache;

    private Cache.Entry entry;

    private ListView listView;

    private FeedClassPostsAdapter listAdapter;

    private List<FeedItem> feedItems;

    private SwipeRefreshLayout refresh;

    private Button notification;

    private Button update;

    private Animation slide;

    private SessionManagement session;

    private static final String UPDATE_SECTION = "http://codevars.esy.es/updatesection.php";

    private String CLASS_FEED = "http://codevars.esy.es/classposts/";

    private Boolean VALID = false;

    private LinearLayout updatepanel;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shelf, container, false);

        session = new SessionManagement(getContext());

        HashMap<String, String> reg = session.getRegistrationDetails();

        HashMap<String, String> dept = session.getDepartmentDetails();

        HashMap<String, String> sec = session.getSectionDetails();

        registrationnumber = reg.get(SessionManagement.REG_NUM);

        department = dept.get(SessionManagement.DEPARTMENT);

        section = sec.get(SessionManagement.SECTION);

        sectionspinner = (Spinner) view.findViewById(R.id.sections);

        listView = (ListView) view.findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedClassPostsAdapter(getActivity(), feedItems);

        listView.setAdapter(listAdapter);

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.newsrefresh);

        refresh.setOnRefreshListener(this);

        notification = (Button) view.findViewById(R.id.notification);

        update = (Button) view.findViewById(R.id.update);

        update.setOnClickListener(this);

        notification.setVisibility(View.GONE);

        updatepanel = (LinearLayout) view.findViewById(R.id.sectionchoose);

        updatepanel.setVisibility(View.GONE);

        loadSpinner();

        infoCheck();

        loadCache();

        return view;

    }


    public boolean isOnline() {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    private void loadSpinner() {

        String sections[] = {"Select Section", "A", "B", "C", "D", "E"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner, sections);

        sectionspinner.setAdapter(adapter);

    }


    private void infoCheck() {

        String[] departments = {"MECH", "CSE", "ECE"};

        String[] sections = {"A", "B", "C", "D", "E"};

        if (Arrays.asList(departments).contains(department) && Arrays.asList(sections).contains(section)) {

            VALID = true;

            if (!CLASS_FEED.contains("json")) {

                CLASS_FEED = CLASS_FEED + department + "-" + section + "/" + department.toLowerCase() + "-" + section.toLowerCase() + ".json";

                getFeedCount();

            }

        } else {

            VALID = false;

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

            notification.setText(getResources().getString(R.string.sectionupdate));

            updatepanel.setVisibility(View.VISIBLE);

        }

    }


    private void slide() {

        slide = new TranslateAnimation(0, 0, 500, 0);

        slide.setDuration(1000);

        notification.setAnimation(slide);

    }


    private void check() {

        selected = sectionspinner.getSelectedItem().toString();

        if (selected.equalsIgnoreCase("Select Section")) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

            notification.setText(getResources().getString(R.string.invalidselection));

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    notification.setText(getResources().getString(R.string.sectionupdate));

                }

            }, 3000);

        } else {

            updateSection(registrationnumber, selected);

        }

    }


    private void updateSection(final String reg, final String sec) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(getContext(), R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equalsIgnoreCase("")) {

                    Toast.makeText(getContext(), "Please Try Again Later!", Toast.LENGTH_LONG).show();

                } else if (s.equalsIgnoreCase("Section Updated Successfully!")) {

                    slide();

                    notification.setVisibility(View.VISIBLE);

                    notification.setBackground(getResources().getDrawable(R.drawable.notificationgreen));

                    notification.setText(s);

                    session.createUpdatedSectionSession(selected);

                    HashMap<String, String> sec = session.getSectionDetails();

                    section = sec.get(SessionManagement.SECTION);

                    VALID = true;

                    CLASS_FEED = CLASS_FEED + department + "-" + section + "/" + department.toLowerCase() + "-" + section.toLowerCase() + ".json";

                    updatepanel.setVisibility(View.GONE);

                    getFeedCount();

                } else {

                    Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("registrationnumber", params[0]);
                data.put("section", params[1]);

                String result = ruc.sendPostRequest(UPDATE_SECTION, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(reg, sec);

    }


    private void loadCache() {

        cache = AppController.getInstance().getRequestQueue().getCache();

        entry = cache.get(CLASS_FEED);

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

        }

    }


    private void refreshResults() {

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, CLASS_FEED, null, new Response.Listener<JSONObject>() {

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

                item.setName(feedObj.getString("name"));

                item.setPrivilege(feedObj.getString("privilege"));

                item.setImage(feedObj.getString("image"));

                item.setTitle(feedObj.getString("title"));

                item.setStatus(feedObj.getString("status"));

                item.setSubject(feedObj.getString("subject"));

                item.setProfilePic(feedObj.getString("profilepic"));

                item.setTime(feedObj.getString("uploadtime"));

                item.setUrl(feedObj.getString("url"));

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


    private void feedCount(JSONObject response) {

        int count = 0;

        try {

            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {

                count = count + 1;

            }

            onlinefeedlength = count;

            offlinefeedlength = feedItems.size();

            posts = onlinefeedlength - offlinefeedlength;

            if (feedItems != null) {

                feedItems.clear();

                refreshResults();

            }

            if (posts >= 1) {

                slide();

                notification.setVisibility(View.VISIBLE);

                notification.setBackground(getResources().getDrawable(R.drawable.notificationgreen));

                notification.setText(posts + " New Posts");

            } else {

                if (notification.getVisibility() == View.VISIBLE) {

                    notification.setVisibility(View.GONE);

                } else {

                    slide();

                    notification.setVisibility(View.VISIBLE);

                    notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

                    notification.setText("No New Posts");

                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            notification.setVisibility(View.GONE);

                        }

                    }, 3000);

                }

            }

        } catch (JSONException e) {

            e.printStackTrace();

        }

    }


    private void getFeedCount() {

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, CLASS_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                VolleyLog.d(TAG, "Response: " + response.toString());

                if (response != null) {

                    feedCount(response);

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


    @Override
    public void onRefresh() {

        if (isOnline()) {

            if (VALID) {

                Toast.makeText(getContext(), "Crunching Class Posts!", Toast.LENGTH_SHORT).show();

                getFeedCount();

            } else {

                refresh.setRefreshing(false);

                slide();

                notification.setVisibility(View.VISIBLE);

                notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

                notification.setText(getResources().getString(R.string.sectionupdate));

            }

        } else {

            Toast.makeText(getContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onClick(View view) {

        if (view == update) {

            check();

        }

    }

}
