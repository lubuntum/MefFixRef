package com.example.memfixref.ui.mainfragments.plots.totalstats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import database.entities.Kit;

public class TotalStatsByKitViewModel extends ViewModel {
    private Kit kit;
    private MutableLiveData<Boolean> sessionByKeyIsLoad;
    private MutableLiveData<Boolean> sessionListsIsLoad;
    private MutableLiveData<Boolean> sessionValueByImageIsLoad;
    public TotalStatsByKitViewModel(Kit kit){
        this.kit = kit;
        sessionByKeyIsLoad =  new MutableLiveData<>();
        sessionListsIsLoad = new MutableLiveData<>();
        sessionValueByImageIsLoad = new MutableLiveData<>();
    }

    public Kit getKit() {
        return kit;
    }

    public void setSessionByKeyIsLoad(boolean isLoad){
        sessionByKeyIsLoad.postValue(isLoad);
    }
    public void setSessionListsIsLoad(boolean isLoad){
        sessionListsIsLoad.postValue(isLoad);
    }
    public void setSessionImageByValueIsLoad(boolean isLoad){
        sessionValueByImageIsLoad.postValue(isLoad);
    }

    public MutableLiveData<Boolean> getSessionByKeyIsLoad() {
        return sessionByKeyIsLoad;
    }

    public MutableLiveData<Boolean> getSessionListsIsLoad() {
        return sessionListsIsLoad;
    }

    public MutableLiveData<Boolean> getSessionValueByImageIsLoad() {
        return sessionValueByImageIsLoad;
    }
}
