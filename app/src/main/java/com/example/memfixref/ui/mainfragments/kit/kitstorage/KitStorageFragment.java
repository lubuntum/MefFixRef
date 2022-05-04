package com.example.memfixref.ui.mainfragments.kit.kitstorage;

import android.content.Intent;
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

import com.example.memfixref.ChangeKitActivity;
import com.example.memfixref.R;
import com.example.memfixref.ui.dialog.KitDialogFragment;
import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import database.entities.Kit;

public class KitStorageFragment extends Fragment {
    public KitStorageViewModel kitStorageViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kit_storage,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        kitStorageViewModel = new ViewModelProvider(getActivity()).get(KitStorageViewModel.class);
        FloatingActionButton actionButton = view.findViewById(R.id.addCellFloatBtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeKitActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //
        //Kit list init
        ListView kitList = view.findViewById(R.id.kitListView);
        //При повторной иниц фрагмента viewModel уже будет создан
        //Следовательно нужно загрузить данные
        //В первый раз адаптер установится только когда загрузиться LiveData и сработает Observer
        if (kitStorageViewModel.getKitAdapter() != null){
            kitList.setAdapter(kitStorageViewModel.getKitAdapter());
            kitStorageViewModel.getKitAdapter().notifyDataSetChanged();
        }

        kitStorageViewModel.getKitListLive().observe(getViewLifecycleOwner(),
                new Observer<List<Kit>>(){
            @Override
            public void onChanged(List<Kit> kits) {
                kitStorageViewModel.setKitAdapter(
                        new KitAdapter(getContext(), R.layout.kit_item, kits));
                kitList.setAdapter(kitStorageViewModel.getKitAdapter());
                //else kitStorageViewModel.getKitAdapter().notifyDataSetChanged();
            }
        });


        kitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Kit kit = kitStorageViewModel.getKitListLive().getValue().get(i);//Получаем кликнутый обьект
                Intent intent = new Intent(getContext(),ChangeKitActivity.class);
                intent.putExtra("kit",kit);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                getActivity().startActivity(intent);
            }
        });
        kitList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                KitDialogFragment kitDialogFragment = KitDialogFragment.newInstance(i);
                kitDialogFragment.show(getChildFragmentManager(),"remove_kit_" + i);
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (kitStorageViewModel.getKitAdapter() != null)
            kitStorageViewModel.uploadKitListLive();
    }
}
