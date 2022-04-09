package com.example.memfixref.ui.mainfragments.session.bykey;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;
import services.DateFormat;
import services.MushIndexes;
import services.PromptHelper;

public class SessionByKeyViewModel extends ViewModel {
    public static final String SESSION_TYPE_KEY = "key";
    public static final String SESSION_TYPE_VALUE = "value";
    public Kit kit;
    public int[] cellIndexes;
    private Cell currentCell;

    private PromptHelper helper;
    public boolean isSessionRunning = true;
    private Session session;

    public SessionByKeyViewModel(Kit kit){
        this.kit = kit;
        MushIndexes mushIndexes = new MushIndexes();
        cellIndexes = mushIndexes.getMushIndexes(kit.cells);


        session = new Session();
        session.kitId = kit.id;
        session.sessionType = SESSION_TYPE_KEY;
        session.setKit(new MutableLiveData<>(kit));
        DateFormat dateFormat = new DateFormat();
        session.useDate = dateFormat.getCurrentDateWithFormat();

        helper = new PromptHelper();
    }

    public Session getSession() {
        return session;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCellByIndex(int index) {
        this.currentCell = kit.cells.get(index);
        helper.updateWord(this.currentCell.key);
    }

    public void setSessionRunning(boolean param){
        this.isSessionRunning = param;
    }


    public String getPrompt(){
        session.prompt++;
        return helper.getNextPrompt();
    }
}
