package com.augmentis.ayp.yeutsen.fragment.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.fragment.main.firststretch.FirstStretchFragment;
import com.augmentis.ayp.yeutsen.fragment.main.setting.SettingAboutFragment;
import com.augmentis.ayp.yeutsen.fragment.main.setting.SettingNotificationFragment;
import com.augmentis.ayp.yeutsen.fragment.main.setting.SettingPreviewSetTimeFragment;
import com.augmentis.ayp.yeutsen.fragment.main.setting.SettingTutorialFragment;
import com.augmentis.ayp.yeutsen.fragment.main.strertchlist.StretchListFragment;
import com.augmentis.ayp.yeutsen.fragment.main.summary.SummaryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private AppBarLayout appBarLayout;
    private Toolbar toolbarMainFragment;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private int[] tabIcons = {

            //icon color black
            R.drawable.home_selected,
            R.drawable.list_selected,
            R.drawable.summary_selected
    };

    private int[] tabBlackIcons = {
            //icon color white
            R.drawable.home_unselected,
            R.drawable.list_unselected,
            R.drawable.summary_unselected
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar_layout);

        toolbarMainFragment = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarMainFragment);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        setHasOptionsMenu(true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.home_selected);
                        tabLayout.getTabAt(1).setIcon(R.drawable.list_unselected);
                        tabLayout.getTabAt(2).setIcon(R.drawable.summary_unselected);
                        appBarLayout.setExpanded(true);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Title");

                        break;
                    case 1:
                        tabLayout.getTabAt(0).setIcon(R.drawable.home_unselected);
                        tabLayout.getTabAt(1).setIcon(R.drawable.list_selected);
                        tabLayout.getTabAt(2).setIcon(R.drawable.summary_unselected);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("ListItem");
                        appBarLayout.setExpanded(true);
//                        getSupportActionBar().setTitle("ListItem");
                        break;
                    case 2:

                        tabLayout.getTabAt(0).setIcon(R.drawable.home_unselected);
                        tabLayout.getTabAt(1).setIcon(R.drawable.list_unselected);
                        tabLayout.getTabAt(2).setIcon(R.drawable.summary_selected);
                        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Summary");
                        appBarLayout.setExpanded(true);
//                        getSupportActionBar().setTitle("Summary");
                        break;
                }

            }



            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //  setRetainInstance(true);
        return view;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.home_selected);
        tabLayout.getTabAt(1).setIcon(R.drawable.list_unselected);
        tabLayout.getTabAt(2).setIcon(R.drawable.summary_unselected);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment( FirstStretchFragment.newInstance(), "ONE");
        adapter.addFragment( StretchListFragment.newInstance(), "TWO");
        adapter.addFragment( SummaryFragment.newInstance(), "THREE");
        viewPager.setAdapter(adapter);

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_main,menu);

        Log.d(TAG, "onCreateOptionsMenu: ");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_set_time :
                Fragment fragmentSetTime = SettingPreviewSetTimeFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentSetTime).commit();
                Log.d(TAG, " set time : " + R.id.mnu_set_time);
                return true;
            case R.id.mnu_tutorial :
                Fragment fragment1Tutorial = SettingTutorialFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment1Tutorial).addToBackStack(null).commit();
                Log.d(TAG, " tutorial : " + R.id.mnu_tutorial);
                return true;
            case R.id.mnu_notification :
                Fragment fragmentNotification = SettingNotificationFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentNotification).addToBackStack(null).commit();
                return true;
            case R.id.mnu_about :
                Fragment fragmentAbout = SettingAboutFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentAbout).addToBackStack(null).commit();
                Log.d(TAG, " about : " + R.id.mnu_about);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}