package com.example.memfixref.ui.mainfragments.kit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.dialog.CellDialogFragment;

import database.entities.Cell;

public class KitFragment extends Fragment {
    ListView cellListView;
    ArrayAdapter<Cell> arrayAdapter;
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

        cellListView  = view.findViewById(R.id.cellListView);
        arrayAdapter = new ArrayAdapter<Cell>(getContext(), android.R.layout.simple_list_item_1,kitViewModel.getCellList());
        cellListView.setAdapter(arrayAdapter);

        addCellBtn.setOnClickListener(v->{
            FragmentManager fragmentManager = getChildFragmentManager();
            CellDialogFragment cellDialogFragment = CellDialogFragment.newInstance();
            cellDialogFragment.show(fragmentManager,"fragment_add_cell");
        });
    }
}
