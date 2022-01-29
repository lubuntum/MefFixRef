package com.example.memfixref.ui.mainfragments.kit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.entities.Cell;
import database.entities.Kit;

public class KitViewModel extends AndroidViewModel {
    //private List<Cell> cellList;
    private Kit kit;
    public KitViewModel(@NonNull Application application) {
        super(application);
        kit = new Kit();
        kit.cells = new LinkedList<>();
        kit.cells.add(new Cell(String.valueOf(new Random().nextInt()),"TestValue"));
    }

    public Kit getKit() {
        return kit;
    }
}
