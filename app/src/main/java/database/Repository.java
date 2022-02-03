package database;

import android.app.Application;
import android.content.Context;
import android.media.Ringtone;
import android.os.AsyncTask;


import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import database.Dao.CellDao;
import database.Dao.KitDao;
import database.Dao.SessionDao;
import database.entities.Cell;
import database.entities.Kit;

public class Repository {
    private Database appDatabase;
    private Executor diskIOExecutor;

    public KitDao kitDao;
    public CellDao cellDao;
    public SessionDao sessionDao;

    public static volatile Repository INSTANCE = null;

    public static Repository getInstance(Application app){
        if (INSTANCE == null){
            synchronized (Repository.class){
                if (INSTANCE == null){
                    INSTANCE = new Repository(app);
                }
            }
        }
        return INSTANCE;
    }

    public Repository(Application app){
        appDatabase = Database.getInstance(app);
        diskIOExecutor = Executors.newCachedThreadPool();
        kitDao = appDatabase.kitDao();
        cellDao = appDatabase.cellDao();
        sessionDao = appDatabase.sessionDao();
    }
    public void insertKitWithCells(Kit kit){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Передать связанные сущности в явном виде посольку Cell отдельные сущности в Бд
                cellDao.insert(kit,kit.cells);
            }
        };
        diskIOExecutor.execute(runnable);
    }
    public void updateKitWithCells(Kit kit){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cellDao.update(kit,kit.cells);
            }
        };
        diskIOExecutor.execute(runnable);
    }
    public Kit getKit(String kitName){
        return kitDao.getKitByName(kitName);
    }
    /**
    public void insertCell (List<Cell> cells){
        new InsertCellsAsyncTask(cellDao).execute(cells);
    }
    public List<Cell> getCellByKitId(int kitId){
        return cellDao.getCellsByKitId(kitId);
    }

    private static class InsertCellsAsyncTask extends
            AsyncTask<List<Cell>,Void,Void>{
        private CellDao cellDao;
        public InsertCellsAsyncTask(CellDao cellDao){
            this.cellDao = cellDao;
        }
        @Override
        protected Void doInBackground(List<Cell>... lists) {
            cellDao.insertAll(lists[0]);
            return null;
        }
    }
        */
}

