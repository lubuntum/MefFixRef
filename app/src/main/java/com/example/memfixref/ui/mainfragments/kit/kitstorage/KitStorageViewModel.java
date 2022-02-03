package com.example.memfixref.ui.mainfragments.kit.kitstorage;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.Repository;
import database.entities.Cell;
import database.entities.Kit;

public class KitStorageViewModel extends AndroidViewModel {

    private List<Kit> kitList;
    private ArrayAdapter<Kit> kitAdapter;
    private Repository repo;

    public KitStorageViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        kitList = new LinkedList<>();
        testKitListInit();
    }

    private void testKitListInit(){
        for(int i = 0 ; i < 5;i++){
            Kit kit = new Kit("KitName" + i);
            kit.frequencyUse = 51 + ((new Random().nextInt())%50);
            kit.lastUse = "02.02.2022";
            kitList.add(kit);
            kit.cells = new LinkedList<>();
            kit.cells.add(new Cell("key" + i,"value" + "i"));
        }
    }

    public ArrayAdapter<Kit> getKitAdapter() {
        return kitAdapter;
    }

    public void setKitAdapter(ArrayAdapter<Kit> kitAdapter) {
        this.kitAdapter = kitAdapter;
    }

    public List<Kit> getKitList() {
        return kitList;
    }

    public void setKitList(List<Kit> kitList) {
        this.kitList = kitList;
    }
}
