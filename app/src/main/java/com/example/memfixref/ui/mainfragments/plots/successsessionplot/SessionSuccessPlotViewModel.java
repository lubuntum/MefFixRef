package com.example.memfixref.ui.mainfragments.plots.successsessionplot;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.LinkedList;
import java.util.List;

import database.Repository;
import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;

/*
* По сути нужно было сделать заранее cells - Mutable для асинхронной загрузки в
* самом Kit, но в начале я прошляпил и не сделал, так что теперь она идет
* отдельным полем в данном классе, что бы была возможность ее асинхронно подгрузить
* вообще по SOLID и по человечески было бы лучше сделать ее внутренним полем класса Kit
* Но сейчас проще и быстрее сделать как было задумано изначально, потому что иначе
* нужно переделывать onekitstorage...
* */
public class SessionSuccessPlotViewModel extends AndroidViewModel {
    private Repository repo;
    private Kit kit;
    private List<DataEntry> barChartData;
    public SessionSuccessPlotViewModel(@NonNull Application application, Kit kit) {
        super(application);
        this.repo = Repository.getInstance(application);
        this.kit = kit;
        if (this.kit.sessionList == null)
            this.kit.sessionList = new MutableLiveData<>();
        barChartData = new LinkedList<>();
        //если входящий kit без прогруженных session
        repo.getAllSessionByKitId(kit.id,kit.sessionList);


    }
    public void sessionParse(){
        for (Session session: kit.sessionList.getValue()) {
            barChartData.add(new charBarDataEntry(session.useDate,session.correct,-session.incorrect));
        }
    }
    private class charBarDataEntry extends ValueDataEntry {

        public charBarDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2",value2);
        }
    }

    public List<DataEntry> getBarChartData() {
        return barChartData;
    }

    public Kit getKit() {
        return kit;
    }
}
