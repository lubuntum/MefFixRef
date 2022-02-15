package com.example.memfixref.ui.mainfragments.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.R;

public class SettingsFragment extends Fragment {
    private SettingsViewModel settingsViewModel;
    private Switch themeSwitchBtn;
    private Switch statisticsSwitchBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        return inflater.inflate(R.layout.fragment_settings,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        themeSwitchBtn = view.findViewById(R.id.themeSwitchBtn);
        statisticsSwitchBtn = view.findViewById(R.id.statisticSwitchBtn);

        if (settingsViewModel.getTheme().equals(SettingsViewModel.BLACK_THEME))
            themeSwitchBtn.setChecked(true);
        statisticsSwitchBtn.setChecked(settingsViewModel.isStatisticsAllowed());

        themeSwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isAllow) {
                if (isAllow)
                    settingsViewModel.setTheme(SettingsViewModel.BLACK_THEME);
                else
                    settingsViewModel.setTheme(SettingsViewModel.WHITE_THEME);
                //Log.d("THEME",settingsViewModel.getTheme());
            }

        });
        statisticsSwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isAllow) {
                settingsViewModel.setStatistics(isAllow);
                //Log.d("USE_STATISTICS",String.valueOf(settingsViewModel.isStatisticsAllowed()));
            }
        });
    }
}
