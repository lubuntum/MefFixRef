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

public class ChangeUserquoteDialogFragment extends DialogFragment {
    private SettingsViewModel settingsViewModel;

    public static ChangeUserquoteDialogFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChangeUserquoteDialogFragment fragment = new ChangeUserquoteDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(getActivity()).get(SettingsViewModel.class);
        return inflater.inflate(R.layout.change_userquote_dialog,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BootstrapButton confirmBtn = view.findViewById(R.id.confirmBtn);
        EditText usernameEditText = view.findViewById(R.id.userQuoteEditText);
        confirmBtn.setOnClickListener((View v)->{
            String quote = usernameEditText.getText().toString();
            settingsViewModel.getEditor().putString(SettingsViewModel.QUOTE,quote);
            settingsViewModel.getEditor().apply();
            Toast.makeText(getContext(),getResources().getString(R.string.toast_success_save_quote),Toast.LENGTH_SHORT).show();
        });
    }
}
