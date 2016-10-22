package com.srmvdp.huddle.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.srmvdp.huddle.MainActivity;
import com.srmvdp.huddle.R;

public class NewsFragment extends Fragment implements View.OnClickListener{


    public NewsFragment() {

    }

    private Button news;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        news = (Button) view.findViewById(R.id.newsget);

        news.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View view) {

        if (view == news) {

            Intent go = new Intent(getContext(), MainActivity.class);

            startActivity(go);

        }

    }

}
