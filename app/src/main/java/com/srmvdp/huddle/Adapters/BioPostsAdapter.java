package com.srmvdp.huddle.Adapters;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.srmvdp.huddle.News.AppController;
import com.srmvdp.huddle.News.FeedImageView;
import com.srmvdp.huddle.News.FeedItem;
import com.srmvdp.huddle.R;
import java.util.List;

public class BioPostsAdapter extends BaseAdapter {

    private TextView timestamp;

    private TextView title;

    private NetworkImageView profilepic;

    private FeedItem item;

    private Activity activity;

    private LayoutInflater inflater;

    private List<FeedItem> feedItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public BioPostsAdapter(Activity activity, List<FeedItem> feedItems) {

        this.activity = activity;

        this.feedItems = feedItems;

    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (inflater == null) {

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.feed_bio, null);

        }

        if (imageLoader == null) {

            imageLoader = AppController.getInstance().getImageLoader();

        }

        timestamp = (TextView) convertView.findViewById(R.id.timestamp);

        title = (TextView) convertView.findViewById(R.id.verificationbar);

        profilepic = (NetworkImageView) convertView.findViewById(R.id.profilePic);

        item = feedItems.get(position);

        setTitle();

        setTimestamp();

        setProfilePic();

        return convertView;

    }


    private void setTitle() {

        if (!TextUtils.isEmpty(item.getTitle())) {

            title.setText(item.getTitle());

            title.setVisibility(View.VISIBLE);

        } else {

            title.setVisibility(View.GONE);

        }

    }


    private void setTimestamp() {

        if (!TextUtils.isEmpty(item.getTime())) {

            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getTime()), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

            timestamp.setText(timeAgo);

        } else {

            timestamp.setVisibility(View.GONE);

        }

    }


    private void setProfilePic() {

        profilepic.setImageUrl(item.getProfilePic(), imageLoader);

    }


}