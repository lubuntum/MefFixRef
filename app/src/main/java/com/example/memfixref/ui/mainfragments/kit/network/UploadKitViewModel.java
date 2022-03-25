package com.example.memfixref.ui.mainfragments.kit.network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import database.entities.Kit;

public class UploadKitViewModel extends AndroidViewModel {
    private Kit kit;
    public UploadKitViewModel(@NonNull Application application, Kit kit) {
        super(application);
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }
}
