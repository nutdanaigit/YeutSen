package com.augmentis.ayp.yeutsen.fragment.main.setting;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augmentis.ayp.yeutsen.R;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SettingTutorialFragment extends Fragment {
    private Toolbar toolbarTutorial;
    private MyViewPageAdapter myViewPagerAdapter;
    ImagePageAdapter mAdapter;
    private int page = 0;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private Button btnSkip, btnStart;
    private int[] layouts;
    Handler handler;
    private int delay = 10000;
    FragmentManager fm;


    public static SettingTutorialFragment newInstance() {

        Bundle args = new Bundle();

        SettingTutorialFragment fragment = new SettingTutorialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_tutorial,container,false);

        toolbarTutorial  = (Toolbar) view.findViewById(R.id.toolbar_tutorial);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarTutorial);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Tutorial");


        viewPager = (ViewPager) view.findViewById(R.id.view_pager1);

        layouts = new int[]{
                R.layout.fragment_tutorial_s1,
                R.layout.fragment_tutorial_s2,
                R.layout.fragment_tutorial_s3
//                R.layout.fragment_tutorial_s4
        };


        myViewPagerAdapter = new MyViewPageAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);



        return view;
    }



    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class ImagePageAdapter extends FragmentPagerAdapter {
        public ImagePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }

    /**
     * View pager adapter
     */

    private class MyViewPageAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPageAdapter() {


        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
