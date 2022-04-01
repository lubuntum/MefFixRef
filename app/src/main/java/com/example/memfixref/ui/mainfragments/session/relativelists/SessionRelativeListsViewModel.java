package com.example.memfixref.ui.mainfragments.session.relativelists;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.memfixref.R;

import java.util.LinkedList;
import java.util.List;

import database.entities.Cell;
import database.entities.Kit;
import services.MushIndexes;

public class SessionRelativeListsViewModel extends AndroidViewModel {
    private Kit kit;
    private RelativeListAdapter adapterByValue;
    private RelativeListAdapter adapterByKey;
    public SessionRelativeListsViewModel(@NonNull Application application, Kit kit) {
        super(application);
        this.kit = kit;
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

    public RelativeListAdapter getAdapterByKey() {
        return adapterByKey;
    }

    public RelativeListAdapter getAdapterByValue() {
        return adapterByValue;
    }
}
