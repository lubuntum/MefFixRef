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
import com.example.memfixref.ui.mainfragments.kit.onekitdata.KitViewModel;

import org.jetbrains.annotations.NotNull;

import database.entities.Cell;

public class CellDialogFragment extends DialogFragment {
    private EditText cellKeyEditText;
    private EditText cellValueEditText;
    private BootstrapButton confirmBtn;
    private BootstrapButton removeCellBtn;

    private Cell cell;

    public static CellDialogFragment newInstance(int cellIndex) {

        Bundle args = new Bundle();
        args.putInt("index",cellIndex);
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
        KitViewModel kitViewModel = new ViewModelProvider(getActivity()).get(KitViewModel.class);

        cellKeyEditText = view.findViewById(R.id.cellKey);
        cellValueEditText = view.findViewById(R.id.cellValue);
        confirmBtn = view.findViewById(R.id.confirmBtn);
        removeCellBtn = view.findViewById(R.id.removeCellBtn);

        if (getArguments() != null && getArguments().containsKey("index")){
            int index = getArguments().getInt("index");
            cell = kitViewModel.getKit().cells.get(index);
            cellKeyEditText.setText(cell.getKey());
            cellValueEditText.setText(cell.getValue());
        }

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cell == null) {
                    kitViewModel.getKit().cells.add(new Cell(cellKeyEditText.getText().toString(),
                            cellValueEditText.getText().toString()));//мб обновить адаптер нужно
                }
                //если редактируем текущий
                else {
                    cell.key = cellKeyEditText.getText().toString();
                    cell.value = cellValueEditText.getText().toString();
                }
                kitViewModel.getArrayAdapter().notifyDataSetChanged();
                dismiss();
            }
        });
        removeCellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
