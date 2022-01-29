package com.example.memfixref.ui.mainfragments.kit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.entities.Cell;

public class KitViewModel extends AndroidViewModel {
    private List<Cell> cellList;
    public KitViewModel(@NonNull Application application) {
        super(application);
        cellList = new LinkedList<>();
        cellList.add(new Cell(String.valueOf(new Random().nextInt()),"TestValue"));
    }

    public List<Cell> getCellList() {
        return cellList;
    }
}
