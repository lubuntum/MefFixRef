package com.example.memfixref.ui.mainfragments.plots.totalstats;

import androidx.lifecycle.ViewModel;

import database.entities.Kit;

public class TotalStatsByKitViewModel extends ViewModel {
    private Kit kit;
    public TotalStatsByKitViewModel(Kit kit){
        this.kit = kit;
    }

    public Kit getKit() {
        return kit;
    }
}
