package com.example.memfixref.ui.mainfragments.plots.totalstats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.plots.successsessionplot.SessionSuccessPlotFragment;
import com.example.memfixref.ui.mainfragments.session.bykey.SessionByKeyViewModel;
import com.example.memfixref.ui.mainfragments.session.imagebyvalue.ImageByValueViewModel;
import com.example.memfixref.ui.mainfragments.session.relativelists.SessionRelativeListsViewModel;

import database.entities.Kit;

public class TotalStatsByKit extends Fragment {
    TotalStatsByKitViewModel totalStatsViewModel;
    public static TotalStatsByKit newInstance(Kit kit) {

        Bundle args = new Bundle();
        args.putSerializable("kit",kit);
        TotalStatsByKit fragment = new TotalStatsByKit();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null && getArguments().containsKey("kit")){
            Kit kit = (Kit) getArguments().getSerializable("kit");
            totalStatsViewModel = new ViewModelProvider(this, new TotalStatsByKitViewModelFactory(kit))
                    .get(TotalStatsByKitViewModel.class);
        }
        return inflater.inflate(R.layout.fragment_stat_plots,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View fragmentKeyValuePlot = view.findViewById(R.id.fragmentKeyValuePlot);
        View fragmentRelativeLists = view.findViewById(R.id.fragmentRelativeListsPlot);
        View fragmentImageByValue = view.findViewById(R.id.imageByValueContainer);
        FragmentManager fragmentManager = getParentFragmentManager();

        Runnable loadKeyValueRnb = new Runnable() {
            @Override
            public void run() {
                fragmentManager.beginTransaction().add(R.id.fragmentKeyValuePlot,
                        SessionSuccessPlotFragment.newInstance(totalStatsViewModel.getKit(),
                                SessionByKeyViewModel.SESSION_TYPE_KEY),
                        "fragment_plot_key_value")
                        .commit();

                totalStatsViewModel.setSessionByKeyIsLoad(true);
            }
        };

        Runnable loadRelativeListsRnb = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fragmentManager.beginTransaction().add(R.id.fragmentRelativeListsPlot,
                        SessionSuccessPlotFragment.newInstance(totalStatsViewModel.getKit(),
                                SessionRelativeListsViewModel.SESSION_TYPE_RELATIVE_LISTS),
                        "fragment_plot_relative_lists")
                        .commit();

                totalStatsViewModel.setSessionListsIsLoad(true);
            }
        };

        Runnable loadImageByValueRnb = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                fragmentManager.beginTransaction().add(R.id.fragmentValueByImage,
                        SessionSuccessPlotFragment.newInstance(totalStatsViewModel.getKit(),
                                ImageByValueViewModel.SESSION_TYPE_IMAGE_BY_VALUE),"fragment_plot_image_by_value")
                        .commit();
                totalStatsViewModel.setSessionImageByValueIsLoad(true);
            }
        };





        Observer<Boolean> loadRelativeList = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                new Thread(loadRelativeListsRnb).start();
            }
        };
        totalStatsViewModel.getSessionByKeyIsLoad().observe(getViewLifecycleOwner(),loadRelativeList);


        Observer<Boolean> loadImageByValue = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                new Thread(loadImageByValueRnb).start();
            }
        };
        totalStatsViewModel.getSessionListsIsLoad().observe(getViewLifecycleOwner(),loadImageByValue);

        new Thread(loadKeyValueRnb).start();

    }
}
