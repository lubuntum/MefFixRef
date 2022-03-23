package com.example.memfixref.ui.mainfragments.plots.successsessionplot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.charts.Cartesian;
import com.anychart.core.axes.Linear;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LabelsOverlapMode;
import com.anychart.enums.Orientation;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;
import com.example.memfixref.R;

import java.util.List;

import database.entities.Kit;
import database.entities.Session;

public class SessionSuccessPlotFragment extends Fragment {
    private SessionSuccessPlotViewModel sessionSuccessPlotViewModel;

    public static SessionSuccessPlotFragment newInstance(Kit kit) {

        Bundle args = new Bundle();
        args.putSerializable("kit",kit);
        SessionSuccessPlotFragment fragment = new SessionSuccessPlotFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments()!= null && getArguments().containsKey("kit")){
            Kit kit = (Kit)getArguments().getSerializable("kit");
            sessionSuccessPlotViewModel = new ViewModelProvider(
                    this,new SessionSuccessPlotViewModelFactory(getActivity().getApplication(),kit)).
                    get(SessionSuccessPlotViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_session_success_plot,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnyChartView anyChartView = view.findViewById(R.id.sessionSuccessPlot);
        Observer<List<Session>> sessionListObserver = new Observer<List<Session>>() {
            @Override
            public void onChanged(List<Session> sessions) {
                sessionSuccessPlotViewModel.sessionParse();
                Cartesian barChart = AnyChart.bar();
                barChart.animation(true);
                barChart.padding(10d,20d,5d,20d);
                barChart.yScale().stackMode(ScaleStackMode.VALUE);

                barChart.yAxis(0).labels().format(
                        "function(){\n" +
                                "return Math.abs(this.value).toLocaleString();\n" +
                                "}");
                barChart.yAxis(0d).title("mistakes ratio");
                barChart.xAxis(0d).overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

                Linear xAxis1 = barChart.xAxis(1d);
                xAxis1.enabled(true);
                xAxis1.orientation(Orientation.RIGHT);
                xAxis1.overlapMode(LabelsOverlapMode.ALLOW_OVERLAP);

                barChart.title("Kit general progress");

                barChart.interactivity().hoverMode(HoverMode.BY_X);

                barChart.tooltip()
                        .title(false)
                        .separator(true)
                        .displayMode(TooltipDisplayMode.SEPARATED)
                        .positionMode(TooltipPositionMode.POINT)
                        .useHtml(true)
                        .fontSize(12d)
                        .format(
                                "function() {\n" +
                                        "      return '<span style=\"color: #D9D9D9\">$</span>' + Math.abs(this.value).toLocaleString();\n" +
                                        "    }");

                Set set = Set.instantiate();
                set.data(sessionSuccessPlotViewModel.getBarChartData());
                Mapping series1Data = set.mapAs("{x: 'x',value:'value'}");
                Mapping series2Data = set.mapAs("{x: 'x',value:'value2'}");

                Bar series1 = barChart.bar(series1Data);
                series1.name("Correct");
                series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER);
                Bar series2 = barChart.bar(series2Data);
                series2.name("Incorrect");
                series2.tooltip()
                        .position("left")
                        .anchor(Anchor.RIGHT_CENTER);
                barChart.legend().enabled(true);
                barChart.legend().inverted(true);
                barChart.legend().fontSize(13d);
                barChart.legend().padding(0d, 0d, 20d, 0d);
                anyChartView.setChart(barChart);

            }

        };
        sessionSuccessPlotViewModel.getKit().
                sessionList.observe(getViewLifecycleOwner(),sessionListObserver);
    }
}
