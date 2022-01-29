package database;

import android.app.Application;
import android.media.Ringtone;
import android.os.AsyncTask;


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

    public Repository(Application app){
        appDatabase = Database.getInstance(app);
        diskIOExecutor = Executors.newCachedThreadPool();
        kitDao = appDatabase.kitDao();
        cellDao = appDatabase.cellDao();
        sessionDao = appDatabase.sessionDao();
    }
    public void insertKitWithCells(Kit kit, List<Cell> cells){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cellDao.insert(kit,cells);
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

