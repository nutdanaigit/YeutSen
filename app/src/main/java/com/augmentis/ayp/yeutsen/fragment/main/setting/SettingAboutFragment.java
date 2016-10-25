package com.augmentis.ayp.yeutsen.fragment.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.augmentis.ayp.yeutsen.R;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SettingAboutFragment extends Fragment {

    TextView textViewAbout;

    Toolbar toolbarAbout;

    public static SettingAboutFragment newInstance() {

        Bundle args = new Bundle();

        SettingAboutFragment fragment = new SettingAboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_about, container, false);


        toolbarAbout = (Toolbar) view.findViewById(R.id.toolbar_about);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarAbout);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About");

        textViewAbout = (TextView) view.findViewById(R.id.about_us);
        textViewAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm =getActivity().getSupportFragmentManager();
                Fragment fragmentSettingAbout = SettingAboutUsFragment.newInstance();
                fm.beginTransaction().replace(R.id.fragment_container,fragmentSettingAbout).addToBackStack(null).commit();
            }
        });


        return view;


    }
}
