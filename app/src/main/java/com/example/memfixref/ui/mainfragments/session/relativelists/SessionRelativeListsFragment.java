package com.example.memfixref.ui.mainfragments.session.relativelists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.R;

import database.entities.Kit;

public class SessionRelativeListsFragment extends Fragment {
    public static SessionRelativeListsFragment newInstance(Kit kit) {

        Bundle args = new Bundle();
        args.putSerializable("kit",kit);
        SessionRelativeListsFragment fragment = new SessionRelativeListsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    SessionRelativeListsViewModel relativeListsViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null && getArguments().containsKey("kit")){
            Kit kit = (Kit)getArguments().getSerializable("kit");
            relativeListsViewModel =
                    new ViewModelProvider(this,
                            new SessionRelativeListsViewModelFactory(getActivity().getApplication(),kit))
                            .get(SessionRelativeListsViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_relative_lists_session,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView keyListView = view.findViewById(R.id.keyListView);
        ListView valueListView = view.findViewById(R.id.valueListView);
        keyListView.setAdapter(relativeListsViewModel.getAdapterByKey());
        valueListView.setAdapter(relativeListsViewModel.getAdapterByValue());
    }
}
