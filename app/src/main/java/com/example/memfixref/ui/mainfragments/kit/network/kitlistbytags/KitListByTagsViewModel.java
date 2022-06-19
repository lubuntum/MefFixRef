package com.example.memfixref.ui.mainfragments.kit.network.kitlistbytags;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;

import java.util.List;

import database.entities.Kit;

public class KitListByTagsViewModel extends ViewModel {
    private List<Kit> kits;
    private KitAdapter kitAdapter;
    public KitListByTagsViewModel(List<Kit> kits){
        this.kits = kits;
    }

    public KitAdapter getKitAdapter() {
        return kitAdapter;
    }

    public void setKitAdapter(KitAdapter kitAdapter) {
        this.kitAdapter = kitAdapter;
    }

    public List<Kit> getKits() {
        return kits;
    }
}
