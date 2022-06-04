package com.example.memfixref.ui.mainfragments.session.imagebyvalue;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import database.entities.Kit;

public class ImageByValueViewModelFactory implements ViewModelProvider.Factory {
    private Kit kit;
    private Application app;

    public ImageByValueViewModelFactory(Application app, Kit kit){
        this.app = app;
        this.kit = kit;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T)new ImageByValueViewModel(app,kit);
    }
}
