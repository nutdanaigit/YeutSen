package com.augmentis.ayp.yeutsen.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.yeutsen.fragment.register.WelcomeFragment;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;
import com.augmentis.ayp.yeutsen.activity.MainActivity;
import com.augmentis.ayp.yeutsen.R;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SplashFragment extends Fragment {
    private static final String TAG = "SplashFragment";
    private static final int DELAY = 5000;


    public static SplashFragment newInstance() {
        Bundle args = new Bundle();
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash,container,false);

        callStartFirst();
        return view;
    }

    /**
     *  Call TutorialFragment or MainActivity
     */
    private void callStartFirst() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Second fragment after 5 seconds appears
                FragmentManager fm = getActivity().getSupportFragmentManager();
                if(!YeutSenPreference.isFirstEnter(getActivity())) {
                    Fragment fragmentT = WelcomeFragment.newInstance();
                    fm.beginTransaction().replace(R.id.fragment_container, fragmentT).commit();
                }else{
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        }, DELAY);

    }
}
