package com.augmentis.ayp.yeutsen.fragment.main.summary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.database.StretchLogLab;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SummaryFragment extends Fragment {

    private static final String TAG = "SummaryStretch";
    private GraphView weeklyGraphView;
    private TextView totalStretch;
    private TextView monthlyStretch;
    private TextView weeklyStretch;
    private TextView todayStretch;

    public static SummaryFragment newInstance() {

        Bundle args = new Bundle();

        SummaryFragment fragment = new SummaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private BarGraphSeries<DataPoint> setWeeklyGraph(String[] weekday){
        double[] weekdayDouble = new double[7];
        for(int i = 0; i < weekday.length; i++)
        {
            weekdayDouble[i] = Double.parseDouble(weekday[i]);
        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 0), //set space
                new DataPoint(1, weekdayDouble[0]),
                new DataPoint(2, weekdayDouble[1]),
                new DataPoint(3, weekdayDouble[2]),
                new DataPoint(4, weekdayDouble[3]),
                new DataPoint(5, weekdayDouble[4]),
                new DataPoint(6, weekdayDouble[5]),
                new DataPoint(7, weekdayDouble[6]),
                new DataPoint(8, 0) //set space
        });
        series.setSpacing(20);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb(249, 168, 37);
            }
        });
        series.setAnimated(true);
        return series;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary,container,false);

        totalStretch = (TextView) view.findViewById(R.id.total_stretches);
        monthlyStretch = (TextView) view.findViewById(R.id.monthly_stretches);
        weeklyStretch = (TextView) view.findViewById(R.id.weekly_stretches);
        todayStretch = (TextView) view.findViewById(R.id.today_stretches);


        weeklyGraphView = (GraphView) view.findViewById(R.id.graph);
        StaticLabelsFormatter staticLabelFormatter = new StaticLabelsFormatter(weeklyGraphView);
        staticLabelFormatter.setHorizontalLabels(new String[]{"","S", "M", "T", "W", "Th", "F", "Sa", ""});
        weeklyGraphView.getGridLabelRenderer().setLabelFormatter(staticLabelFormatter);


        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: " + isVisibleToUser);
        if(isVisibleToUser == true){
            weeklyGraphView.addSeries(setWeeklyGraph(StretchLogLab.getInstance(getContext()).queryWeekdayStetch()));
            totalStretch.setText(StretchLogLab.getInstance(getContext()).queryTotalStretch());
            monthlyStretch.setText(StretchLogLab.getInstance(getContext()).queryMonthlyStretch());
            weeklyStretch.setText(StretchLogLab.getInstance(getContext()).queryWeeklyStretch());
            todayStretch.setText(StretchLogLab.getInstance(getContext()).queryTodayStretch());

        }else{
            if(weeklyGraphView != null){
                weeklyGraphView.removeAllSeries();
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StretchLogLab.getInstance(getActivity()).queryMonthlyStretch();
    }
}
