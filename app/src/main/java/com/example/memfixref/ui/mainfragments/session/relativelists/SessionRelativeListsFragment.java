package com.example.memfixref.ui.mainfragments.session.relativelists;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
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
    private View pickedViewByKey;
    private View pickedViewByValue;
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

        keyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (pickedViewByKey != null && pickedViewByKey.equals(view)){
                    pickedViewByKey.setBackground(getResources().getDrawable(R.drawable.gray_borders));
                    pickedViewByKey = null;
                    relativeListsViewModel.setPickedCellFromListByKey(null);
                }
                else {
                    drawPickedElement(view, pickedViewByKey);
                    pickedViewByKey = view;
                    relativeListsViewModel.setPickedCellFromListByKey
                            (relativeListsViewModel.getAdapterByKey().getItem(i));
                }

            }
        });
        valueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (pickedViewByValue != null && pickedViewByValue.equals(view)){
                    pickedViewByValue.setBackground(getResources().getDrawable(R.drawable.gray_borders));
                    pickedViewByValue = null;
                    relativeListsViewModel.setPickedCellFromListByValue(null);
                }
                else {
                    drawPickedElement(view, pickedViewByValue);
                    pickedViewByValue = view;
                    relativeListsViewModel.setPickedCellFromListByValue
                            (relativeListsViewModel.getAdapterByValue().getItem(i));
                }
            }
        });
    }
    public void drawPickedElement(View view, View pickedView){
        GradientDrawable gd = new GradientDrawable();
        if (pickedView != null) {
            //gd.setColor(getResources().getColor(R.color.white));
            //gd.setStroke(1,getResources().getColor(R.color.bootstrap_gray_lighter));
            //pickedView.setBackground(gd);
            pickedView.setBackground(getResources().getDrawable(R.drawable.gray_borders));
        }
        view.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_success));
    }
}
