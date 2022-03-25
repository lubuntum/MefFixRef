package com.example.memfixref.ui.mainfragments.kit.network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import database.entities.Kit;

public class UploadKitViewModelFactory implements ViewModelProvider.Factory {
    Kit kit;
    Application app;
    public UploadKitViewModelFactory(Application app, Kit kit){
        this.app = app;
        this.kit = kit;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T)new UploadKitViewModel(app,kit);
    }
}
