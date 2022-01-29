package com.example.memfixref.ui.mainfragments.kit;

import android.app.Application;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.Repository;
import database.entities.Cell;
import database.entities.Kit;

public class KitViewModel extends AndroidViewModel {
    //private List<Cell> cellList;
    private Kit kit;
    private ArrayAdapter<Cell> arrayAdapter;
    private Repository repo;
    private LiveData<String> kitName;
    public KitViewModel(@NonNull Application application) {
        super(application);
        repo = new Repository(application);

        kitName = new MutableLiveData<>();
        kit = new Kit();
        kit.cells = new LinkedList<>();
        kit.cells.add(new Cell(String.valueOf(new Random().nextInt()),"TestValue"));
    }

    public Kit getKit() {
        return kit;
    }
    public void saveKit(){
        repo.insertKitWithCells(kit,kit.cells);
    }

    public LiveData<String> getKitName() {
        return kitName;
    }

    public ArrayAdapter<Cell> getArrayAdapter() {
        return arrayAdapter;
    }

    public void setArrayAdapter(ArrayAdapter<Cell> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }
}
