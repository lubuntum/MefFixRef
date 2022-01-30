package com.example.memfixref.ui.mainfragments.kit.kitstorage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.ChangeKitActivity;
import com.example.memfixref.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KitStorageFragment extends Fragment {
    public KitStorageViewModel kitStorageViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        kitStorageViewModel = new ViewModelProvider(this).get(KitStorageViewModel.class);
        return inflater.inflate(R.layout.fragment_kit_storage,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton actionButton = view.findViewById(R.id.addCellFloatBtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeKitActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                getActivity().startActivity(intent);
            }
        });
    }
}
