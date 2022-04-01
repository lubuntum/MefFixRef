package com.example.memfixref.ui.mainfragments.session.endresult;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import database.entities.Session;

public class SessionResultViewModelFactory implements ViewModelProvider.Factory {
    private Session session;
    private Application app;
    public SessionResultViewModelFactory(@NonNull Application application,Session session) {
        //super(application);
        this.app = application;
        this.session = session;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new SessionResultViewModel(app,session);

    }


}
