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

public class ChangeUsernameDialogFragment extends DialogFragment {
    private SettingsViewModel settingsViewModel;
    public static ChangeUsernameDialogFragment newInstance() {

        Bundle args = new Bundle();

        ChangeUsernameDialogFragment fragment = new ChangeUsernameDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        return inflater.inflate(R.layout.change_username_dialog,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BootstrapButton confirmBtn = view.findViewById(R.id.confirmBtn);
        EditText usernameEditText = view.findViewById(R.id.userNameEditText);
        confirmBtn.setOnClickListener((View v)->{
            if (usernameEditText.getText().toString().length() >= 5){
                String username = usernameEditText.getText().toString();
                settingsViewModel.getEditor().putString(SettingsViewModel.USERNAME,username);
                settingsViewModel.getEditor().apply();
                Toast.makeText(getContext(),"Username changed successfully",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(),"Not less then five words",Toast.LENGTH_SHORT).show();
        });
    }
}
