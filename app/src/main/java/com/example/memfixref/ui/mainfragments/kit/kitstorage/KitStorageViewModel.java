package com.example.memfixref.ui.mainfragments.kit.kitstorage;

import android.app.Application;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.Repository;
import database.entities.Cell;
import database.entities.Kit;

public class KitStorageViewModel extends AndroidViewModel {

    private MutableLiveData<List<Kit>> kitListLive;
    private ArrayAdapter<Kit> kitAdapter;
    private Repository repo;

    public KitStorageViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        kitListLive = new MutableLiveData<>();
        uploadKitListLive();

        //kitList = new LinkedList<>();
        //testKitListInit();
    }
    public void uploadKitListLive(){
        repo.getAllKits(kitListLive);
    }
    public void removeKit(Kit kit){
        if(kitListLive.getValue().contains(kit)){
            try {
                repo.removeKit(kit);
                if (kitAdapter != null) {
                    kitAdapter.remove(kit);
                    kitAdapter.notifyDataSetChanged();
                }
            }
            catch (Exception e){
                Toast.makeText(getApplication(),
                        getApplication().getResources().getString(R.string.toast_reload_app),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public ArrayAdapter<Kit> getKitAdapter() {
        return kitAdapter;
    }

    public void setKitAdapter(ArrayAdapter<Kit> kitAdapter) {
        this.kitAdapter = kitAdapter;
    }

    public MutableLiveData<List<Kit>> getKitListLive() {
        return kitListLive;
    }

    public void setKitListLive(MutableLiveData<List<Kit>> kitListLive) {
        this.kitListLive = kitListLive;
    }
}
