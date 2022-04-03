package com.example.memfixref.ui.mainfragments.session.relativelists;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.memfixref.R;

import java.util.LinkedList;
import java.util.List;

import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;
import services.DateFormat;
import services.MushIndexes;

public class SessionRelativeListsViewModel extends AndroidViewModel {
    private Kit kit;
    private RelativeListAdapter adapterByValue;
    private RelativeListAdapter adapterByKey;
    private Cell pickedCellFromListByKey;
    private Cell pickedCellFromListByValue;

    private Session session;

    public SessionRelativeListsViewModel(@NonNull Application application, Kit kit) {
        super(application);
        this.kit = kit;
        session = new Session();
        session.kitId = kit.id;
        session.setKit(new MutableLiveData<>(kit));
        DateFormat dateFormat = new DateFormat();
        session.useDate = dateFormat.getCurrentDateWithFormat();

        MushIndexes mushIndexes = new MushIndexes();
        //Два массива с перемешенными индексами для списков
        int [] mushIndexesByKey = mushIndexes.getMushIndexes(kit.cells);
        List<Cell> cellListByKey = new LinkedList<>();

        int [] mushIndexesByValue = mushIndexes.getMushIndexes(kit.cells);
        List<Cell> cellListByValue = new LinkedList<>();
        for(int i = 0; i < kit.cells.size();i++){
            cellListByKey.add(kit.cells.get(mushIndexesByKey[i]));
            cellListByValue.add(kit.cells.get(mushIndexesByValue[i]));
        }
        adapterByKey = new RelativeListAdapter(application, R.layout.relative_list_item,cellListByKey,false);
        adapterByValue = new RelativeListAdapter(application,R.layout.relative_list_item,cellListByValue,true);

    }

    public Kit getKit() {
        return kit;
    }

    public Session getSession() {
        return session;
    }

    public RelativeListAdapter getAdapterByKey() {
        return adapterByKey;
    }

    public RelativeListAdapter getAdapterByValue() {
        return adapterByValue;
    }

    public Cell getPickedCellFromListByKey() {
        return pickedCellFromListByKey;
    }

    public void setPickedCellFromListByKey(Cell pickedCellFromListByKey) {
        this.pickedCellFromListByKey = pickedCellFromListByKey;
    }

    public Cell getPickedCellFromListByValue() {
        return pickedCellFromListByValue;
    }

    public void setPickedCellFromListByValue(Cell pickedCellFromListByValue) {
        this.pickedCellFromListByValue = pickedCellFromListByValue;
    }
    public boolean checkPickedCells(){
        if (pickedCellFromListByKey != null && pickedCellFromListByValue != null){
            return pickedCellFromListByKey.equals(pickedCellFromListByValue);
        }
        return false;
    }
    public void removePickedCells(){
        adapterByKey.remove(pickedCellFromListByKey);
        adapterByValue.remove(pickedCellFromListByValue);
        pickedCellFromListByKey = null;
        pickedCellFromListByValue = null;
    }
}
