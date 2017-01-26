package com.srmvdp.huddle.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.srmvdp.huddle.Adapters.FeedNewsAdapter;
import com.srmvdp.huddle.News.AppController;
import com.srmvdp.huddle.News.FeedItem;
import com.srmvdp.huddle.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public NewsFragment() {}

    private static final String TAG = NewsFragment.class.getSimpleName();

    private int offlinefeedlength;

    private int onlinefeedlength;

    private int posts;

    private Cache cache;

    private Cache.Entry entry;

    private ListView listView;

    private FeedNewsAdapter listAdapter;

    private List<FeedItem> feedItems;

    private SwipeRefreshLayout refresh;

    private Button notification;

    private Animation slide;

    private String NEWS_FEED = "http://codevars.esy.es/news/news.json";

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        listView = (ListView) view.findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedNewsAdapter(getActivity(), feedItems);

        listView.setAdapter(listAdapter);

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.newsrefresh);

        refresh.setOnRefreshListener(this);

        notification = (Button) view.findViewById(R.id.notification);

        notification.setOnClickListener(this);

        notification.setVisibility(View.GONE);

        loadCache();

        return view;

    }


    private void slide() {

        slide = new TranslateAnimation(0, 0, 500, 0);

        slide.setDuration(1000);

        notification.setAnimation(slide);

    }


    private void loadCache() {

        cache = AppController.getInstance().getRequestQueue().getCache();

        entry = cache.get(NEWS_FEED);

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

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, NEWS_FEED, null, new Response.Listener<JSONObject>() {

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

                }

                else {

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

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, NEWS_FEED, null, new Response.Listener<JSONObject>() {

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

        Toast.makeText(getContext(), "Crunching Latest News!", Toast.LENGTH_SHORT).show();

        getFeedCount();

    }


    @Override
    public void onClick(View view) {

        notification.setVisibility(View.GONE);

    }


}
