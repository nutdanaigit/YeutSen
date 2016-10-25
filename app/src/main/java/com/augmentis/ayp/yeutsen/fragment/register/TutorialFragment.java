package com.augmentis.ayp.yeutsen.fragment.register;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augmentis.ayp.yeutsen.R;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class TutorialFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "TutorialFragment";
    private static final int DELAY = 4000;
    private int page = 0;
    private int[] layoutsTutorial;

    private TutorialAdapter tutorialAdapter;
    private Button mBtnSkip, mBtnStart;
    private Handler mHandler;
    private ImagePageAdapter mImagePageAdapter;
    private LinearLayout dotsLayout;
    private ViewPager mViewPager;
    private TextView[] dots;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mImagePageAdapter.getCount() == page) {
            } else {
                page++;
            }
            mViewPager.setCurrentItem(page, true);
            mHandler.postDelayed(this, DELAY);
        }
    };
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            page = position;
            addBottomDots(position);

            if (position == 0) {
                mBtnSkip.setVisibility(View.INVISIBLE);
                mBtnStart.setVisibility(View.VISIBLE);
            } else if (position == layoutsTutorial.length - 1) {
                mBtnSkip.setVisibility(View.VISIBLE);
                mBtnSkip.setText(getString(R.string.tutorial_begin));
            } else {
                mBtnSkip.setText(getString(R.string.tutorial_skip));
                mBtnStart.setVisibility(View.INVISIBLE);
                mBtnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public static TutorialFragment newInstance() {
        Bundle args = new Bundle();

        TutorialFragment fragment = new TutorialFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial,container,false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) view.findViewById(R.id.layoutDots);
        mBtnSkip = (Button) view.findViewById(R.id.btn_skip);
        mBtnStart = (Button) view.findViewById(R.id.btn_start);

        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        mBtnSkip.setOnClickListener(this);
        mBtnStart.setOnClickListener(this);

        layoutsTutorial = new int[]{
                R.layout.fragment_tutorial_s1,
                R.layout.fragment_tutorial_s2,
                R.layout.fragment_tutorial_s3
        };

        //adding bottom dots
        addBottomDots(0);

        //making notification bar transparent
        changeStatusBarColor();

        tutorialAdapter = new TutorialAdapter();
        mViewPager.setAdapter(tutorialAdapter);
        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        mHandler = new Handler();
        mImagePageAdapter = new ImagePageAdapter(getActivity().getSupportFragmentManager());


        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_skip:
                int current = getItem(1);
                if (current < layoutsTutorial.length) {
                    mViewPager.setCurrentItem(layoutsTutorial.length - 1);
                } else {
                    launchHomeScreen();
                }
                break;
            case R.id.btn_start:
                launchHomeScreen();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(runnable, DELAY);

    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    /**
     * Inner Class
     */
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

    private class TutorialAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public TutorialAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layoutsTutorial[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layoutsTutorial.length;
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



    /**
     * Method
     * @param currentPage
     */
    private void addBottomDots(int currentPage) {
        dots = new TextView[layoutsTutorial.length];

        int[] colorActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorActive[currentPage]);
    }


    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

    public void launchHomeScreen() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment f = SetTimeFragment.newInstance();
        fm.beginTransaction()
                .replace(R.id.fragment_container, f)
                .commit();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
