package com.example.memfixref.ui.mainfragments.session;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.memfixref.ui.mainfragments.kit.onekitdata.cellist.CellAdapter;

import java.util.LinkedList;
import java.util.List;

import database.Repository;
import database.entities.Cell;
import database.entities.Kit;

public class SessionPrepareViewModel extends AndroidViewModel {

    private MutableLiveData<Kit> pickedKit;
    private MutableLiveData<List<Cell>> cellList;
    private CellAdapter cellAdapter;
    private Repository repo;

    public SessionPrepareViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        //инициализация для первой загрузки
        pickedKit = new MutableLiveData<>();
        cellList = new MutableLiveData<>();
    }

    public void updateCellListWithKit(Kit kit) {
        if (kit.cells == null) kit.cells = new LinkedList<>();
        repo.getAllCellsByKit(kit,cellList);
        this.pickedKit.setValue(kit);
    }
    public void updateKit(Kit kit){
        repo.updateKit(kit);
    }

    public MutableLiveData<Kit> getPickedKit() {
        return pickedKit;
    }

    public CellAdapter getCellAdapter() {
        return cellAdapter;
    }

    public void setCellAdapter(CellAdapter cellAdapter) {
        this.cellAdapter = cellAdapter;
    }

    public MutableLiveData<List<Cell>> getCellList() {
        return cellList;
    }
}
