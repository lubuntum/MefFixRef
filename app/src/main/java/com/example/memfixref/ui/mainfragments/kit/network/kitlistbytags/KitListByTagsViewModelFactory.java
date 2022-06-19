package com.example.memfixref.ui.mainfragments.kit.network.kitlistbytags;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import database.entities.Kit;

public class KitListByTagsViewModelFactory implements ViewModelProvider.Factory {

    private List<Kit> kits;
    public KitListByTagsViewModelFactory(List<Kit> kits){
        this.kits = kits;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new KitListByTagsViewModel(kits);
    }
}
