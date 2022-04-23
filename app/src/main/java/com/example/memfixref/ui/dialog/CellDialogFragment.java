package com.example.memfixref.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private ImageButton addImageBtn;
    private ActivityResultLauncher<Intent> loadImageResultLauncher;

    private Cell cell;

    public static CellDialogFragment newInstance(int cellIndex) {

        Bundle args = new Bundle();
        args.putInt("index",cellIndex);
        CellDialogFragment fragment = new CellDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Обработка выбора изображения
        loadImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Toast.makeText(getContext(),"Image saved",Toast.LENGTH_SHORT).show();
                        // конвертнуть изображение и сохранить его + путь в базу + отобразить в списке
                    }
                }
        );
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
        addImageBtn = view.findViewById(R.id.addImageBtn);

        if (getArguments() != null && getArguments().containsKey("index")){
            int index = getArguments().getInt("index");
            cell = kitViewModel.getKit().cells.get(index);
            cellKeyEditText.setText(cell.getKey());
            cellValueEditText.setText(cell.getValue());
        }

        addImageBtn.setOnClickListener((View v)->{
            Toast.makeText(getContext(),"Show Gallery",Toast.LENGTH_SHORT).show();
            //Вызвать галеррею
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            loadImageResultLauncher.launch(intent);
        });

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
                try {
                    kitViewModel.removeCell(cell);
                }
                catch (Exception e){
                    Toast.makeText(getContext(),
                            getContext().getResources().getString(R.string.toast_reload_app),
                            Toast.LENGTH_LONG).show();
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
