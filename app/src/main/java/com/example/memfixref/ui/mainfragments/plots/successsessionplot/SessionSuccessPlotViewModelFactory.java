package com.example.memfixref.ui.mainfragments.plots.successsessionplot;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import database.entities.Kit;


public class SessionSuccessPlotViewModelFactory implements ViewModelProvider.Factory {
    private Application app;
    private Kit kit;
    private String sessionType;
    public SessionSuccessPlotViewModelFactory(Application app, Kit kit, String sessionType){
        this.app = app;
        this.kit = kit;
        this.sessionType = sessionType;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SessionSuccessPlotViewModel.class))
        return (T) new SessionSuccessPlotViewModel(app,kit,sessionType);
        else throw new ClassCastException();
    }
}
