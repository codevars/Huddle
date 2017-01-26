package com.srmvdp.huddle.Adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
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

public class FeedNewsAdapter extends BaseAdapter {

    private TextView name;

    private TextView timestamp;

    private TextView subject;

    private TextView title;

    private TextView status;

    private TextView url;

    private NetworkImageView profilepic;

    private FeedImageView feedImageView;

    private FeedItem item;

    private ImageView verification;

    private Activity activity;

    private LayoutInflater inflater;

    private List<FeedItem> feedItems;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public FeedNewsAdapter(Activity activity, List<FeedItem> feedItems) {

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

            convertView = inflater.inflate(R.layout.feed_news, null);

        }

        if (imageLoader == null) {

            imageLoader = AppController.getInstance().getImageLoader();

        }


        name = (TextView) convertView.findViewById(R.id.name);

        timestamp = (TextView) convertView.findViewById(R.id.timestamp);

        subject = (TextView) convertView.findViewById(R.id.subject);

        title = (TextView) convertView.findViewById(R.id.title);

        status = (TextView) convertView.findViewById(R.id.status);

        url = (TextView) convertView.findViewById(R.id.url);

        profilepic = (NetworkImageView) convertView.findViewById(R.id.profilePic);

        feedImageView = (FeedImageView) convertView.findViewById(R.id.feedimageview);

        verification = (ImageView) convertView.findViewById(R.id.verification);

        verification.setVisibility(View.GONE);

        item = feedItems.get(position);

        setName();

        setTimestamp();

        setSubject();

        setTitle();

        setStatus();

        setUrl();

        setProfilePic();

        setVerification();

        return convertView;

    }


    private void setName() {

        if (!TextUtils.isEmpty(item.getName())) {

            name.setText(item.getName());


        } else {

            name.setVisibility(View.GONE);

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


    private void setSubject() {

        if (!TextUtils.isEmpty(item.getSubject())) {

            subject.setText(item.getSubject());

            subject.setVisibility(View.VISIBLE);

        } else {

            subject.setVisibility(View.GONE);

        }

    }


    private void setTitle() {

        if (!TextUtils.isEmpty(item.getTitle())) {

            title.setText(item.getTitle());

            title.setVisibility(View.VISIBLE);

        } else {

            title.setVisibility(View.GONE);

        }

    }


    private void setStatus() {

        if (!TextUtils.isEmpty(item.getStatus())) {

            status.setText(item.getStatus());

            status.setVisibility(View.VISIBLE);


        } else {

            status.setVisibility(View.GONE);

        }

    }


    private void setUrl() {

        if (!item.getUrl().equalsIgnoreCase("")) {

            url.setText(item.getUrl());

            url.setVisibility(View.VISIBLE);

        } else {

            url.setVisibility(View.GONE);

        }

    }


    private void setProfilePic() {

        profilepic.setImageUrl(item.getProfilePic(), imageLoader);

        if (!item.getImage().equalsIgnoreCase("")) {

            feedImageView.setImageUrl(item.getImage(), imageLoader);

            feedImageView.setVisibility(View.VISIBLE);

            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });

        } else {

            feedImageView.setVisibility(View.GONE);

        }

    }


    private void setVerification() {

        if (item.getPrivilege().equalsIgnoreCase("Dean") || item.getPrivilege().equalsIgnoreCase("HOD") || item.getPrivilege().equalsIgnoreCase("Admin") || item.getPrivilege().equalsIgnoreCase("Teacher")) {

            verification.setVisibility(View.VISIBLE);

        }

    }

}