package com.srmvdp.huddle.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.srmvdp.huddle.Adapters.CustomGridViewActivity;
import com.srmvdp.huddle.R;

public class SubjectsFragment extends Fragment {

    GridView androidGridView;

    String[] gridViewString = {"Physics", "Civil", "Maths", "Chemistry", "English", "Electrical", "Scilab", "Mechanical"};

    int[] gridViewImageId = {

            R.drawable.physics, R.drawable.civil, R.drawable.maths,

            R.drawable.chemistry, R.drawable.english, R.drawable.electrical,

            R.drawable.scilab, R.drawable.mechanical

    };


    public SubjectsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_subjects, container, false);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(getContext(), gridViewString, gridViewImageId);
        androidGridView = (GridView) view.findViewById(R.id.grid_view_image_text);

        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext(), "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });

        return view;

    }


}

