package com.example.memfixref.ui.mainfragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.R;
import com.example.memfixref.ui.dialog.ChangeUsernameDialogFragment;
import com.example.memfixref.ui.dialog.ChangeUserquoteDialogFragment;
import com.example.memfixref.ui.dialog.SessionTimeDialogFragment;

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
        LinearLayout userNameBtn = view.findViewById(R.id.userNameContainer);
        LinearLayout userQuoteBtn = view.findViewById(R.id.userQuoteContainer);
        FragmentManager fragmentManager = getChildFragmentManager();
        userNameBtn.setOnLongClickListener((View v)->{
            ChangeUsernameDialogFragment usernameDialogFragment = ChangeUsernameDialogFragment.newInstance();
            usernameDialogFragment.show(fragmentManager,"username_dialog");
            return false;
        });
        userQuoteBtn.setOnLongClickListener((View v)->{
            ChangeUserquoteDialogFragment userquoteDialogFragment = ChangeUserquoteDialogFragment.newInstance();
            userquoteDialogFragment.show(fragmentManager,"userquote_dialog");
            return false;
        });
        LinearLayout keyValueSessionBtn = view.findViewById(R.id.sessionTimeKeyValueContainer);
        LinearLayout relativeListsSessionBtn = view.findViewById(R.id.sessionTimeRelativeListContainer);
        LinearLayout randomListsSessionBtn = view.findViewById(R.id.randomListsContainer);
        keyValueSessionBtn.setOnLongClickListener((View v)->{
            SessionTimeDialogFragment sessionTimeDialogFragment = SessionTimeDialogFragment
                    .newInstance(SettingsViewModel.KEY_VALUE_SESSION_TIME);
            sessionTimeDialogFragment.show(fragmentManager,"key_value_session_time_dialog");
            return false;
        });
        relativeListsSessionBtn.setOnLongClickListener((View v)->{
            SessionTimeDialogFragment sessionTimeDialogFragment = SessionTimeDialogFragment
                    .newInstance(SettingsViewModel.RELATIVE_LISTS_SESSION_TIME);
            sessionTimeDialogFragment.show(fragmentManager,"relative_lists_session_time_dialog");
            return false;
        });
        randomListsSessionBtn.setOnLongClickListener((View v)->{
            SessionTimeDialogFragment sessionTimeDialogFragment = SessionTimeDialogFragment
                    .newInstance(SettingsViewModel.RANDOM_LISTS_SESSION_TIME);
            sessionTimeDialogFragment.show(fragmentManager,"random_lists_session_time_dialog");
            return false;
        });
        initSessionTimeHints(view,savedInstanceState);
        //Для диначимеского обновления времени в подсказках
        Observer<Boolean> updateHintsUI = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                initSessionTimeHints(view,savedInstanceState);
            }
        };
        settingsViewModel.getUpdateHintsLiveData().observe(getViewLifecycleOwner(),updateHintsUI);
    }
    public void initSessionTimeHints(View view, Bundle savedInstanceState){
        TextView keyValueTimeHint = view.findViewById(R.id.keyValueTimeHint);
        TextView relativeListsTimeHint = view.findViewById(R.id.relativeListsTimeHint);
        TextView randomListsTimeHint = view.findViewById(R.id.randomListTimeHint);

        String keyValueTimeStr = settingsViewModel.getSettingsPreferences().getString(SettingsViewModel.KEY_VALUE_SESSION_TIME,"15");
        keyValueTimeHint.setText(String.format(getResources().getString(R.string.prefer_time_s),keyValueTimeStr));

        String relativeListsTimeStr = settingsViewModel.getSettingsPreferences().getString(SettingsViewModel.RELATIVE_LISTS_SESSION_TIME,"60");
        relativeListsTimeHint.setText(String.format(getResources().getString(R.string.prefer_time_s),relativeListsTimeStr));

        String randomListsTimeStr = settingsViewModel.getSettingsPreferences().getString(SettingsViewModel.RANDOM_LISTS_SESSION_TIME,"60");
        randomListsTimeHint.setText(String.format(getResources().getString(R.string.prefer_time_s),randomListsTimeStr));
    }
}
