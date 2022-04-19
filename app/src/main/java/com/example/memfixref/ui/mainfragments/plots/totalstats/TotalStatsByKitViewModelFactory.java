package com.example.memfixref.ui.mainfragments.plots.totalstats;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import database.entities.Kit;

public class TotalStatsByKitViewModelFactory implements ViewModelProvider.Factory {
    public Kit kit;
    public TotalStatsByKitViewModelFactory(Kit kit){
        this.kit = kit;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new TotalStatsByKitViewModel(kit);
    }
}
