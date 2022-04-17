package com.example.memfixref.ui.mainfragments.plots.successsessionplot;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;

import java.util.LinkedList;
import java.util.List;

import database.Repository;
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
    private String sessionType;//Какие именно сессии покаказать на графике
    private List<DataEntry> barChartData;
    public SessionSuccessPlotViewModel(@NonNull Application application, Kit kit,String sessionType) {
        super(application);
        this.repo = Repository.getInstance(application);
        this.kit = kit;
        this.sessionType = sessionType;

        this.kit.sessionList = new MutableLiveData<>();
        barChartData = new LinkedList<>();
        repo.getAllSessionBySessionTypeByKitId(kit.id,sessionType,kit.sessionList);


    }
    //Перебор всех сессий и сортировка их содержимого по датам сессий
    public void sessionParse(){
            for (Session session : kit.sessionList.getValue()) {
                boolean isContain = false;
                for (DataEntry barChart : barChartData) {
                    if (barChart.getValue("x").equals(session.useDate)) {
                        barChart.setValue("value", Integer.parseInt(barChart.getValue("value").toString()) + session.correct);
                        barChart.setValue("value2", (Integer.parseInt(barChart.getValue("value2").toString())) - session.incorrect);
                        isContain = true;
                    }
                }
                if (!isContain) {
                    barChartData.add(new CharBarDataEntry(session.useDate,
                            session.correct, -session.incorrect));
                }
            }

        //CharBarDataEntry charBarDataEntry = new CharBarDataEntry("01/01/21",1,2);

    }
    private static class CharBarDataEntry extends ValueDataEntry {
        public CharBarDataEntry(String useDate, Number value, Number value2) {
            super(useDate, value);
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
