package com.example.memfixref.ui.mainfragments.session.sessionbykey;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memfixref.ui.mainfragments.session.SessionPrepareViewModel;

import database.entities.Kit;

public class SessionByKeyViewModel extends ViewModel {
    MutableLiveData<Integer> currentCellIndex;
    Kit kit;
    int[] cellIndexes ;

    public SessionByKeyViewModel(Kit kit){
        currentCellIndex = new MutableLiveData<>();
        this.kit = kit;
        cellIndexes = new int[kit.cells.size()];
    }
}
