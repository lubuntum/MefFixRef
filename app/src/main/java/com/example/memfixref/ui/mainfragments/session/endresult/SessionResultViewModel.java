package com.example.memfixref.ui.mainfragments.session.endresult;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Objects;

import database.Repository;
import database.entities.Kit;
import database.entities.Session;

public class SessionResultViewModel extends AndroidViewModel {
    private Session session;
    private Kit kit;
    private MutableLiveData<List<Session>> sessionList;
    private Repository repo;
    public SessionResultViewModel(@NonNull Application application,Session session) {
        super(application);
        this.repo = Repository.getInstance(application);
        repo.insertSession(session);

        this.session = session;
        this.kit = session.kit.getValue();

        this.sessionList = new MutableLiveData<>();
        loadAllSession();

        //repo.getKitById(session.kitId,session.kit);
    }

    public Kit getKit() { return kit; }
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

    public MutableLiveData<List<Session>> getSessionList(){return sessionList;}

    public void loadAllSession(){
        repo.getAllSessionBySessionTypeByKitId(session.kitId,session.sessionType,sessionList);
    }

}
