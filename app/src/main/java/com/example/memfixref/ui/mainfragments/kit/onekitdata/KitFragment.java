package com.example.memfixref.ui.mainfragments.kit.onekitdata;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import database.entities.Cell;
//FIXME s:
// - Необходимо создать собственный адаптер для отображения cells
// - Доступ к созданию Kit только из HomeFragment, а через боковую панель
// - Доступ ко всем наборам(Вынести KitFragment как простой отдельный фрагмент,
// а не элем боковой панели в layouts, там создать новую и заменить)
// - В новой менюшке боковой панели показывать все оступные kits, так же сделать поиск в DAO, пока нету

public class KitFragment extends Fragment {
    ListView cellListView;
    KitViewModel kitViewModel;
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
        BootstrapButton saveKitBtn  = view.findViewById(R.id.saveBtn);

        kitViewModel.getKitName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });

        cellListView  = view.findViewById(R.id.cellListView);
        kitViewModel.setArrayAdapter(
                new ArrayAdapter<Cell>(getContext(), android.R.layout.simple_list_item_1,kitViewModel.getKit().cells));
        cellListView.setAdapter(kitViewModel.getArrayAdapter());

        addCellBtn.setOnClickListener(v->{
            FragmentManager fragmentManager = getChildFragmentManager();
            CellDialogFragment cellDialogFragment = CellDialogFragment.newInstance();
            cellDialogFragment.show(fragmentManager,"fragment_add_cell");
        });
        saveKitBtn.setOnClickListener(v->{
            try {

                try {
                    kitViewModel.getKit().kitName = kitNameEditText.getText().toString();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Make sure kit have a name or you have empty list", Toast.LENGTH_LONG).show();
                }
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
}
