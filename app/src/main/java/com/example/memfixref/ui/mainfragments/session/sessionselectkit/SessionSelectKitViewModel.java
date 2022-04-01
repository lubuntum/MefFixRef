package com.example.memfixref.ui.mainfragments.session.sessionselectkit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;

import java.util.List;

import database.Repository;
import database.entities.Kit;

public class SessionSelectKitViewModel extends AndroidViewModel {
    private MutableLiveData<List<Kit>> kitListLive;
    private KitAdapter kitAdapter;
    Repository repo;

    public SessionSelectKitViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        kitListLive = new MutableLiveData<>();
        uploadKitListLive();
    }

    public void uploadKitListLive(){
        repo.getAllKits(kitListLive);
    }

    public MutableLiveData<List<Kit>> getKitListLive() {
        return kitListLive;
    }

    public void setKitAdapter(KitAdapter kitAdapter) {
        this.kitAdapter = kitAdapter;
    }

    public KitAdapter getKitAdapter() {
        return kitAdapter;
    }
}
