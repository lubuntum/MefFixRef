package com.example.memfixref.ui.mainfragments.kit.onekitdata;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.dialog.CellDialogFragment;
import com.example.memfixref.ui.mainfragments.kit.kitstorage.KitStorageViewModel;
import com.example.memfixref.ui.mainfragments.kit.onekitdata.CellComponents.CellAdapter;

import java.util.List;

import database.entities.Cell;
import database.entities.Kit;


public class KitFragment extends Fragment {
    ListView cellListView;
    KitViewModel kitViewModel;

    public static KitFragment newInstance(Kit kit) {
        Bundle args = new Bundle();
        args.putSerializable("kit",kit);
        KitFragment fragment = new KitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        kitViewModel = new ViewModelProvider(getActivity()).get(KitViewModel.class);

        return inflater.inflate(R.layout.fragment_kit,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText kitNameEditText = view.findViewById(R.id.kitNameEditText);
        BootstrapButton addCellBtn = view.findViewById(R.id.addCellBtn);
        BootstrapButton changeKitBtn = view.findViewById(R.id.changeKitBtn);

        cellListView  = view.findViewById(R.id.cellListView);
        kitViewModel.getCellList().observe(getViewLifecycleOwner(), new Observer<List<Cell>>() {
            @Override
            public void onChanged(List<Cell> cells) {
                kitViewModel.getKit().cells = cells;
                kitViewModel.setArrayAdapter(
                        new CellAdapter(getContext(), R.layout.cell_item,kitViewModel.getKit().cells));
                cellListView.setAdapter(kitViewModel.getArrayAdapter());
            }
        });
        /*
        Если изменяем набор, а не создаем новый
        В зависимости от этого, кнопка выполняет разные действия с базой
        Если изменяем, то происходит апдейт сущностей, если новый набор то вставка */
        if (getArguments()!= null && getArguments().containsKey("kit")) {
            Kit tempKit = (Kit) getArguments().getSerializable("kit");
            kitViewModel.setKit(tempKit);

            changeKitBtn.setText(getResources().getString(R.string.update));
            kitNameEditText.setText(kitViewModel.getKit().kitName);

            kitViewModel.uploadCells();//загрузка содержимого kit
            changeKitBtn.setOnClickListener(v->{
                try {
                    kitViewModel.updateKit();

                    Toast.makeText(getContext(),"Data edited successfully",Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(getContext(),
                            "Can't edit Kit, please try again, may be something gone wrong",
                            Toast.LENGTH_LONG).show();
                }
                //KitStorageViewModel kitStorageViewModel =
                //        new ViewModelProvider(getActivity()).get(KitStorageViewModel.class);
                //kitStorageViewModel.uploadKitListLive();
            });
        }
        else {
            //Создаем новый набор и устанавливаем LiveData новый набор Cells из Kit
            kitViewModel.setKit(new Kit());
            kitViewModel.getCellList().setValue(kitViewModel.getKit().cells);

            changeKitBtn.setText(getResources().getString(R.string.save));
            changeKitBtn.setOnClickListener(v->{
                try {
                    kitViewModel.saveKit();

                    Toast.makeText(getContext(),"Data saved successfully",Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(getContext(),
                            "Can't save Kit, please try again, may be something gone wrong",
                            Toast.LENGTH_LONG).show();
                }
                //KitStorageViewModel kitStorageViewModel =
                //        new ViewModelProvider(getActivity()).get(KitStorageViewModel.class);
                //kitStorageViewModel.uploadKitListLive();
                //kitStorageViewModel.getKitListLive().getValue().add(kitViewModel.getKit());
                //kitStorageViewModel.getKitAdapter().notifyDataSetChanged();
            });
        }
        /*
        После ввода имени пакета он сохраняется
         */
        kitNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                kitViewModel.getKit().kitName = editable.toString();
            }
        });

        addCellBtn.setOnClickListener(v->{
            FragmentManager fragmentManager = getChildFragmentManager();
            CellDialogFragment cellDialogFragment = CellDialogFragment.newInstance();
            cellDialogFragment.show(fragmentManager,"fragment_add_cell");
        });

    }


    @Override
    public void onStop() {
        super.onStop();
    }
}
