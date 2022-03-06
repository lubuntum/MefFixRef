package com.example.memfixref.ui.mainfragments.session.sessionbykey;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import database.entities.Kit;

public class SessionByKeyViewModelFactory implements ViewModelProvider.Factory {


    Kit kit;
    public SessionByKeyViewModelFactory( Kit kit){
        this.kit = kit;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new SessionByKeyViewModel(kit);
    }
}
