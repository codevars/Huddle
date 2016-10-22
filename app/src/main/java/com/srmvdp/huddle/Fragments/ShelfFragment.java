package com.srmvdp.huddle.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.srmvdp.huddle.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShelfFragment extends Fragment {


    public ShelfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shelf, container, false);


    }

}
