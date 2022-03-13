package com.example.memfixref.ui.mainfragments.session.sessionbykey;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memfixref.ui.mainfragments.session.SessionPrepareViewModel;

import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;
import services.MushIndexes;
import services.PromptHelper;

public class SessionByKeyViewModel extends ViewModel {
    public Kit kit;
    public int[] cellIndexes;
    private Cell currentCell;

    private PromptHelper helper;
    private boolean sessionIsRunning = true;
    private Session session;

    public SessionByKeyViewModel(Kit kit){
        this.kit = kit;
        MushIndexes mushIndexes = new MushIndexes();
        cellIndexes = mushIndexes.getMushIndexes(kit.cells);
        session = new Session();
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

    public void setSessionIsRunning(boolean param){
        this.sessionIsRunning = param;
    }
    public boolean isSessionIsRunning(){
        return this.sessionIsRunning;
    }

    public String getPrompt(){
        session.prompt++;
        return helper.getNextPrompt();
    }
}
