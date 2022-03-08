package com.example.memfixref.ui.mainfragments.session.sessionbykey;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.memfixref.ui.mainfragments.session.SessionPrepareViewModel;

import database.entities.Kit;
import database.entities.Session;
import services.MushIndexes;

public class SessionByKeyViewModel extends ViewModel {
    public Kit kit;
    public int[] cellIndexes ;
    private Session session;

    public SessionByKeyViewModel(Kit kit){
        this.kit = kit;
        MushIndexes mushIndexes = new MushIndexes();
        cellIndexes = mushIndexes.getMushIndexes(kit.cells);
        session = new Session();
    }

    public Session getSession() {
        return session;
    }
}
