package com.example.memfixref.ui.mainfragments.session.sessionendresult;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import database.Repository;
import database.entities.Session;

public class SessionResultViewModel extends AndroidViewModel {
    private Session session;
    private Repository repo;
    public SessionResultViewModel(@NonNull Application application,Session session) {
        super(application);
        this.repo = Repository.getInstance(application);
    }

    public Session getSession() {
        return session;
    }
}
