package com.example.memfixref.ui.mainfragments.session.bykey;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import database.entities.Kit;

public class SessionByKeyViewModelFactory implements ViewModelProvider.Factory {


    private Kit kit;
    private Application app;
    public SessionByKeyViewModelFactory(Application app, Kit kit){
        this.kit = kit;
        this.app = app;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new SessionByKeyViewModel(app, kit);
    }
}
