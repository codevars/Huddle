package com.srmvdp.huddle;

import com.srmvdp.huddle.Adapters.FeedListAdapter;
import com.srmvdp.huddle.News.AppController;
import com.srmvdp.huddle.News.FeedItem;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

	private static final String TAG = MainActivity.class.getSimpleName();

	private ListView listView;

	private FeedListAdapter listAdapter;

	private List<FeedItem> feedItems;

    private SwipeRefreshLayout refresh;

	private String URL_FEED = "http://codevars.esy.es/results.json";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.list);

		feedItems = new ArrayList<FeedItem>();

		listAdapter = new FeedListAdapter(this, feedItems);

		listView.setAdapter(listAdapter);


        refresh = (SwipeRefreshLayout) findViewById(R.id.newsrefresh);

        refresh.setOnRefreshListener(this);


		// We first check for cached request
		Cache cache = AppController.getInstance().getRequestQueue().getCache();
		Entry entry = cache.get(URL_FEED);
		if (entry != null) {
			// fetch the data from cache
			try {
				String data = new String(entry.data, "UTF-8");
				try {
					parseJsonFeedInitial(new JSONObject(data));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else {
			// making fresh volley request and getting json

            refreshResults();

		}

	}



    private void refreshResults() {

        JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
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

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);

    }



    private void parseJsonFeedInitial(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                final FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
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

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
				String image = feedObj.isNull("image") ? null : feedObj
						.getString("image");
				item.setImge(image);
				item.setStatus(feedObj.getString("status"));
				item.setProfilePic(feedObj.getString("profilePic"));
				item.setTimeStamp(feedObj.getString("timeStamp"));

				// url might be null sometimes
				String feedUrl = feedObj.isNull("url") ? null : feedObj
						.getString("url");
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



    @Override
    public void onRefresh() {

        Toast.makeText(this, "Crunching Latest News...", Toast.LENGTH_SHORT).show();

        if(feedItems!=null) {

            feedItems.clear();

            refreshResults();

        }




    }



}
