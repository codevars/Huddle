package com.srmvdp.huddle.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.srmvdp.huddle.Adapters.FeedListAdapter;
import com.srmvdp.huddle.News.AppController;
import com.srmvdp.huddle.News.FeedItem;
import com.srmvdp.huddle.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public NewsFragment() {}

    private static final String TAG = NewsFragment.class.getSimpleName();

    private ListView listView;

    private FeedListAdapter listAdapter;

    private List<FeedItem> feedItems;

    private SwipeRefreshLayout refresh;

    private String URL_FEED = "http://codevars.esy.es/results.json";

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        listView = (ListView) view.findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(getActivity(), feedItems);

        listView.setAdapter(listAdapter);

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.newsrefresh);

        refresh.setOnRefreshListener(this);

        Cache cache = AppController.getInstance().getRequestQueue().getCache();

        Cache.Entry entry = cache.get(URL_FEED);

        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeedRefresh(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {

            onRefresh();

        }

        return view;

    }


    private void refreshResults() {

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    parseJsonFeedRefresh(response);
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


    private void parseJsonFeedRefresh(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                final FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj.getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTime(feedObj.getString("uploadtime"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj.getString("url");
                item.setUrl(feedUrl);

                listView.post(new Runnable() {
                    @Override
                    public void run() {
                        feedItems.add(0, item);
                        listAdapter.notifyDataSetChanged();
                        listView.smoothScrollToPosition(0);
                    }
                });
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();

            refresh.setRefreshing(false);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRefresh() {

        Toast.makeText(getContext(), "Crunching Latest News...", Toast.LENGTH_SHORT).show();

        if (feedItems != null) {

            feedItems.clear();

            refreshResults();

        }

    }

}
