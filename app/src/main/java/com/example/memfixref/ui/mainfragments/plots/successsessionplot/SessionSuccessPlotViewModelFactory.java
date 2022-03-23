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
    public SessionSuccessPlotViewModelFactory(Application app, Kit kit){
        this.app = app;
        this.kit = kit;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SessionSuccessPlotViewModel.class))
        return (T) new SessionSuccessPlotViewModel(app,kit);
        else throw new ClassCastException();
    }
}
