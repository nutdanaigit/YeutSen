package com.augmentis.ayp.yeutsen.fragment.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.yeutsen.R;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SettingAboutUsFragment extends Fragment {
    Toolbar toolbarAboutUs;

    public static SettingAboutUsFragment newInstance() {

        Bundle args = new Bundle();

        SettingAboutUsFragment fragment = new SettingAboutUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_about_us,container,false);


        toolbarAboutUs = (Toolbar) view.findViewById(R.id.toolbar_about_us);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarAboutUs);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("About Us");

        return view;

    }
}
