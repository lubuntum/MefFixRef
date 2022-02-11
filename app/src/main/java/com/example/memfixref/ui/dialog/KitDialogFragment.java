package com.example.memfixref.ui.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.kitstorage.KitStorageViewModel;

import database.entities.Kit;

public class KitDialogFragment extends DialogFragment {
    BootstrapButton removeKitBtn;
    BootstrapButton cancelBtn;
    TextView removeKitTextView;
    int kitIndex;
    Kit kit;
    public static KitDialogFragment newInstance(int kitIndex) {

        Bundle args = new Bundle();
        args.putInt("kitIndex",kitIndex);
        KitDialogFragment fragment = new KitDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState){
        return inflater.inflate(R.layout.remove_kit_dialog,group,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        KitStorageViewModel kitStorageViewModel;
        kitStorageViewModel =
                new ViewModelProvider(getActivity()).get(KitStorageViewModel.class);
        removeKitBtn = view.findViewById(R.id.removeKitBtn);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        removeKitTextView = view.findViewById(R.id.removeKitTextView);
        try {
            kitIndex = getArguments().getInt("kitIndex");
            kit = kitStorageViewModel.getKitListLive().getValue().get(kitIndex);//Получаем нужный Kit
            removeKitTextView.setText(String.format(getContext().getResources().getString(R.string.remove_kit_with_cells_format),kit.kitName));
        }
        catch (Exception e){
            Toast.makeText(getContext(),
                    getContext().getResources().getString(R.string.toast_fail_kit_remove),
                    Toast.LENGTH_LONG).show();
            dismiss();
        }


        removeKitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    kitStorageViewModel.removeKit(kit);
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.toast_success_kit_remove),
                            Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(getContext(),
                            getResources().getString(R.string.toast_reload_app) ,
                            Toast.LENGTH_LONG).show();
                }
                dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
