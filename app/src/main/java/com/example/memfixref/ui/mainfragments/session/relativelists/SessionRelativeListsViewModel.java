package com.example.memfixref.ui.mainfragments.session.relativelists;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.settings.SettingsViewModel;

import java.util.LinkedList;
import java.util.List;

import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;
import services.DateFormat;
import services.MushIndexes;

public class SessionRelativeListsViewModel extends AndroidViewModel {
    public static final String SESSION_TYPE_RELATIVE_LISTS = "relative_lists";
    private Kit kit;
    private RelativeListAdapter adapterByValue;
    private RelativeListAdapter adapterByKey;
    private Cell pickedCellFromListByKey;
    private Cell pickedCellFromListByValue;

    public Boolean SessionRunning;

    private Session session;
    private int progressBarDelay;

    public SessionRelativeListsViewModel(@NonNull Application application, Kit kit) {
        super(application);
        this.kit = kit;

        SharedPreferences sharedPreferences = application.getSharedPreferences(
                SettingsViewModel.SETTINGS_STORAGE, Context.MODE_PRIVATE);
        progressBarDelay = Integer.parseInt(
                sharedPreferences.getString(SettingsViewModel.RELATIVE_LISTS_SESSION_TIME,"15")) * 10;

        SessionRunning = true;
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
    public void adaptersInit(){
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
        adapterByKey = new RelativeListAdapter(getApplication(), R.layout.relative_list_item,cellListByKey,false);
        adapterByValue = new RelativeListAdapter(getApplication(),R.layout.relative_list_item,cellListByValue,true);
    }
    //Удалить значения из адаптера
    public void removePickedCells(){
        adapterByKey.remove(pickedCellFromListByKey);
        adapterByValue.remove(pickedCellFromListByValue);
        pickedCellFromListByKey = null;
        pickedCellFromListByValue = null;
    }
    //Сбросить значения
    public void resetPickedCells(){
        pickedCellFromListByKey = null;
        pickedCellFromListByValue = null;
    }
    public void setSessionRunning(boolean param){
        this.SessionRunning = param;
    }

    public int getProgressBarDelay() {
        return progressBarDelay;
    }

    public void createSession(){
        session = Session.createSession(SESSION_TYPE_RELATIVE_LISTS,kit.id);
        session.setKit(new MutableLiveData<>(kit));
    }
}
