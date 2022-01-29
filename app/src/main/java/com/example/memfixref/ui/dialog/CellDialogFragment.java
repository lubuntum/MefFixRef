package com.example.memfixref.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.KitViewModel;

import org.jetbrains.annotations.NotNull;

import database.entities.Cell;

public class CellDialogFragment extends DialogFragment {
    private EditText cellKeyEditText;
    private EditText cellValueEditText;
    private BootstrapButton confirmBtn;

    public static CellDialogFragment newInstance() {

        Bundle args = new Bundle();

        CellDialogFragment fragment = new CellDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState){
        return inflater.inflate(R.layout.add_cell_dialog,group,false);
    }
    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState){
        cellKeyEditText = view.findViewById(R.id.cellKey);
        cellValueEditText = view.findViewById(R.id.cellValue);
        confirmBtn = view.findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KitViewModel kitViewModel = new ViewModelProvider(getActivity()).get(KitViewModel.class);
                kitViewModel.getCellList().add(new Cell(cellKeyEditText.getText().toString(),
                        cellValueEditText.getText().toString()));//мб обновить адаптер нужно
                dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
