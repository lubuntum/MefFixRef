package com.example.memfixref.ui.mainfragments.kit.network.upload;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.memfixref.ui.mainfragments.kit.onekitdata.cellist.CellAdapter;
import com.example.memfixref.ui.mainfragments.settings.SettingsViewModel;

import database.entities.Kit;

public class UploadKitViewModel extends AndroidViewModel {
    private Kit kit;
    private SharedPreferences preferences;
    private CellAdapter cellAdapter;
    public UploadKitViewModel(@NonNull Application application, Kit kit) {
        super(application);
        this.kit = kit;
        this.preferences = application.getSharedPreferences(SettingsViewModel.SETTINGS_STORAGE, Context.MODE_PRIVATE);
    }

    public Kit getKit() {
        return kit;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public CellAdapter getCellAdapter() {
        return cellAdapter;
    }

    public void setCellAdapter(CellAdapter cellAdapter) {
        this.cellAdapter = cellAdapter;
    }
}
