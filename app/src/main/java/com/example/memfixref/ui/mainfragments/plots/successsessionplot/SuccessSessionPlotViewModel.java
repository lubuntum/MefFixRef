package com.example.memfixref.ui.mainfragments.plots.successsessionplot;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import database.Repository;

public class SuccessSessionPlotViewModel extends AndroidViewModel {
    Repository repo;
    public SuccessSessionPlotViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
    }
}
