package com.example.memfixref.ui.mainfragments.session.sessionselectkit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;
import com.example.memfixref.ui.mainfragments.session.SessionPrepareViewModel;

import java.util.List;

import database.entities.Kit;

public class SessionSelectKitFragment extends Fragment {
    SessionSelectKitViewModel selectKitViewModel;
    ListView kitListView;
    public static SessionSelectKitFragment getInstance(){
        Bundle args = new Bundle();
        SessionSelectKitFragment fragment = new SessionSelectKitFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        selectKitViewModel = new ViewModelProvider(getActivity()).get(SessionSelectKitViewModel.class);
        return inflater.inflate(R.layout.fragment_session_select_kit,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kitListView = view.findViewById(R.id.kitListView);
        kitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SessionPrepareViewModel sessionPrepareViewModel =
                        new ViewModelProvider(getActivity()).get(SessionPrepareViewModel.class);
                //устанавливаем модели выбранный kit
                sessionPrepareViewModel.updateCellListWithKit(selectKitViewModel.getKitListLive().getValue().get(i));
                getParentFragmentManager().popBackStack();
            }
        });
        Observer<List<Kit>> kitLoadObserver = new Observer<List<Kit>>() {
            @Override
            public void onChanged(List<Kit> kits) {
                selectKitViewModel.setKitAdapter(new KitAdapter(
                        getContext(),
                        R.layout.kit_item,
                        kits));
                kitListView.setAdapter(selectKitViewModel.getKitAdapter());
            }
        };
        selectKitViewModel.getKitListLive().observe(getViewLifecycleOwner(),kitLoadObserver);
    }
}
