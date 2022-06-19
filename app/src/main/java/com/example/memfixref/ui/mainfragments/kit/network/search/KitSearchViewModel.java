package com.example.memfixref.ui.mainfragments.kit.network.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import database.entities.Kit;
import database.web.APIKitServices;
import database.web.WebRepository;
import database.web.jsondeserializer.KitListDeserializer;

public class KitSearchViewModel extends ViewModel {
    private KitAdapter kitAdapter;
    private MutableLiveData<List<Kit>> kits;
    private APIKitServices apiKitServices;
    public KitSearchViewModel(){
        kits = new MutableLiveData<>();
    }

    public void setKitAdapter(KitAdapter kitAdapter) {
        this.kitAdapter = kitAdapter;
    }

    public KitAdapter getKitAdapter() {
        return kitAdapter;
    }


    public MutableLiveData<List<Kit>> getKits() {
        return kits;
    }

}
