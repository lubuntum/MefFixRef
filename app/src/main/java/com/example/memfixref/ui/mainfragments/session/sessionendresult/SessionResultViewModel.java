package com.example.memfixref.ui.mainfragments.session.sessionendresult;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.Objects;

import database.Repository;
import database.entities.Session;

public class SessionResultViewModel extends AndroidViewModel {
    private Session session;
    private Repository repo;
    public SessionResultViewModel(@NonNull Application application,Session session) {
        super(application);
        this.repo = Repository.getInstance(application);
        repo.insertSession(session);
        this.session = session;

        //repo.getKitById(session.kitId,session.kit);
    }

    public Session getSession() {
        return session;
    }
    public String getKitName(){
        return Objects.requireNonNull(session.kit.getValue()).kitName;
    };
    public String getSessionPrompt(){
        return String.valueOf(session.prompt);
    }
    public String getIncorrect(){
        return String.valueOf(session.incorrect);
    }
    public String getCorrect(){
        return String.valueOf(session.correct);
    }
}
