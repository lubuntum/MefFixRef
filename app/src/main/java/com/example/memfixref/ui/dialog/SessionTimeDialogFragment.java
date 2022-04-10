package com.example.memfixref.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.settings.SettingsViewModel;

/**
 * typeSessionTime содержит имя константы время которой настраивается.
 * К примеру если передать константу KEY_VALUE_SESSION_TIME, то время будет настроенно
 * именно для сессий по key и value. Тоесть в SharedPreference будет сохранено время
 * по переданному ключу, и потом этот ключ будет использован в соответствующей сессии
 */
public class SessionTimeDialogFragment extends DialogFragment {
    private SettingsViewModel settingsViewModel;
    private String typeSessionTime;
    public static SessionTimeDialogFragment newInstance(final String typeSessionTime) {

        Bundle args = new Bundle();
        args.putString("type_session_time",typeSessionTime);
        SessionTimeDialogFragment fragment = new SessionTimeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        if (getArguments()!= null && getArguments().containsKey("type_session_time")){
            this.typeSessionTime = getArguments().getString("type_session_time");
        }
        return inflater.inflate(R.layout.set_session_time_dialog,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BootstrapButton confirm = view.findViewById(R.id.confirmBtn);
        EditText sessionTimeEditText = view.findViewById(R.id.sessionTimeEditText);
        confirm.setOnClickListener((View v)->{
            String sessionTime = sessionTimeEditText.getText().toString();
            settingsViewModel.getEditor().putString(typeSessionTime,sessionTime);
            settingsViewModel.getEditor().apply();
            settingsViewModel.getUpdateHintsLiveData().setValue(true);
            Toast.makeText(getContext(),
                    getResources().getString(R.string.toast_success_set_session_time),
                    Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }
}
