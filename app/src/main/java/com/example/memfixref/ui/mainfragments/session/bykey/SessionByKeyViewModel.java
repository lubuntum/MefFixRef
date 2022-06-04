package com.example.memfixref.ui.mainfragments.session.bykey;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memfixref.ui.mainfragments.settings.SettingsViewModel;

import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;
import services.DateFormat;
import services.MushIndexes;
import services.PromptHelper;

public class SessionByKeyViewModel extends AndroidViewModel {
    public static final String SESSION_TYPE_KEY = "key";
    public static final String SESSION_TYPE_VALUE = "value";

    public Kit kit;
    public int[] cellIndexes;
    private Cell currentCell;

    private PromptHelper helper;

    public boolean isSessionRunning = true;
    private Session session;

    private int progressBarDelay = 1000;//default ms


    public SessionByKeyViewModel(Application app, Kit kit){
        super(app);
        this.kit = kit;
        MushIndexes mushIndexes = new MushIndexes();
        cellIndexes = mushIndexes.getMushIndexes(kit.cells.size());

        SharedPreferences sharedPreferences = app.getSharedPreferences(
                SettingsViewModel.SETTINGS_STORAGE,
                Context.MODE_PRIVATE);
        progressBarDelay = Integer.parseInt(
                sharedPreferences.getString(SettingsViewModel.KEY_VALUE_SESSION_TIME,"15"))*10;


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

    public int getProgressBarDelay() {
        return progressBarDelay;
    }
    public void createSession(){
        session = Session.createSession(SESSION_TYPE_KEY,kit.id);
        session.setKit(new MutableLiveData<>(kit));

    }
}
