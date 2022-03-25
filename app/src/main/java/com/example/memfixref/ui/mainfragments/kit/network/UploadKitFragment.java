package com.example.memfixref.ui.mainfragments.kit.network;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.plots.successsessionplot.SessionSuccessPlotViewModelFactory;

import database.entities.Kit;

public class UploadKitFragment extends Fragment {
    public static UploadKitFragment newInstance(Kit kit) {

        Bundle args = new Bundle();
        args.putSerializable("kit",kit);
        UploadKitFragment fragment = new UploadKitFragment();
        fragment.setArguments(args);
        return fragment;
    }
    UploadKitViewModel uploadKitViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey("kit")){
            Kit kit = (Kit)getArguments().getSerializable("kit");
            uploadKitViewModel = new ViewModelProvider(this,new UploadKitViewModelFactory(getActivity().getApplication(),kit)).get(UploadKitViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_submit_kit,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView userAgreementText = view.findViewById(R.id.userAgreementText);
        CheckBox userAgreementCheckBox = view.findViewById(R.id.userAgreementCheck);
        userAgreementCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) userAgreementText.setTextColor(getResources().getColor(R.color.bootstrap_brand_success));
                else userAgreementText.setTextColor(getResources().getColor(R.color.bootstrap_gray_light));
            }
        });

        TextView kitNameText = view.findViewById(R.id.kitNameTextView);
        kitNameText.setText(uploadKitViewModel.getKit().kitName);
    }
}
