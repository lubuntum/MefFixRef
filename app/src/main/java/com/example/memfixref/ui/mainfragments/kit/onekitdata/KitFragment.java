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
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.dialog.CellDialogFragment;
import com.example.memfixref.ui.mainfragments.kit.onekitdata.CellComponents.CellAdapter;

import database.entities.Kit;
//FIXME s:
// - Необходимо создать собственный адаптер для отображения cells 3 //сделано
// - Сделать свой адаптер для списка Kit и отобразитб все сохраненные kit в данном списке 2 //сделано(без базы)
// - По клику сделать редактирование выбранного Kit 4//сделано(без базы)
// - В новой менюшке боковой панели показывать все доступные kits, так же сделать поиск в DAO, пока нету 1
// - Доступ к созданию Kit только из HomeFragment, а не через боковую панель //сделано
// - Доступ ко всем наборам(Вынести KitFragment как простой отдельный фрагмент, //сделано
// а не элем боковой панели в layouts, там создать новую и заменить) //сделано
// - Начать работать со статистикой, сделать фрагмент Stats
// - Сделать фрагмент Settings
// - Проетистировать и разодраться с CellDao, проверить работу Insert, Update, каскадное удаление/ обновление
// - Разобрать с кнопкой сохранения в KitFragment


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
        /*
        Если изменяем набор, а не создаем новый
        В зависимости от этого, кнопка выполняет разные действия с базой
        Если изменяем, то происходит апдейт сущностей, если новый набор то вставка */
        if (getArguments()!= null && getArguments().containsKey("kit")) {
            Kit tempKit = (Kit) getArguments().getSerializable("kit");
            kitViewModel.setKit(tempKit);

            changeKitBtn.setText(getResources().getString(R.string.update));
            kitNameEditText.setText(kitViewModel.getKit().kitName);
            changeKitBtn.setOnClickListener(v->{
                try {
                    kitViewModel.updateKit();
                }
                catch (Exception e){
                    Toast.makeText(getContext(),
                            "Can't edit Kit, please try again, may be something gone wrong",
                            Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(),"Data edit successfully",Toast.LENGTH_LONG).show();
            });
        }
        else {
            changeKitBtn.setText(getResources().getString(R.string.save));
            changeKitBtn.setOnClickListener(v->{
                try {
                    kitViewModel.saveKit();
                }
                catch (Exception e){
                    Toast.makeText(getContext(),
                            "Can't save Kit, please try again, may be something gone wrong",
                            Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getContext(),"Data save successfully",Toast.LENGTH_LONG).show();
            });
        }
        //событие отслеживает изменение EditText, в совю очередь изменяя LiveData,
        // которая в своб очередь изменяет имя в kit
        //Выглядит довольно бугорно
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

        cellListView  = view.findViewById(R.id.cellListView);
        kitViewModel.setArrayAdapter(
                new CellAdapter(getContext(), R.layout.cell_item,kitViewModel.getKit().cells));
        cellListView.setAdapter(kitViewModel.getArrayAdapter());

        addCellBtn.setOnClickListener(v->{
            FragmentManager fragmentManager = getChildFragmentManager();
            CellDialogFragment cellDialogFragment = CellDialogFragment.newInstance();
            cellDialogFragment.show(fragmentManager,"fragment_add_cell");
        });

    }
}
