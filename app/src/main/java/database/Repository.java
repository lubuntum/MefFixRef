package database;

import android.app.Application;
import android.content.Context;
import android.media.Ringtone;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import database.Dao.CellDao;
import database.Dao.KitDao;
import database.Dao.SessionDao;
import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;

public class Repository {
    private Database appDatabase;
    private Executor diskIOExecutor;
    private Executor mainThreadExecutor;

    public KitDao kitDao;
    public CellDao cellDao;
    public SessionDao sessionDao;

    public static volatile Repository INSTANCE = null;

    public volatile List<Kit> kitList;

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
        //Ссылка для выполнения операций в главном потоке (UI)
        mainThreadExecutor = new Executor() {
            private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
            @Override
            public void execute(Runnable runnable) {
                mainThreadHandler.post(runnable);
            }
        };

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

    public void removeKit(Kit kit){
        Runnable removeKitRnb = new Runnable() {
            @Override
            public void run() {
                    kitDao.deleteKit(kit);
            }
        };
        diskIOExecutor.execute(removeKitRnb);
    }
    public void getKitById(long kitId, MutableLiveData<Kit> kit){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                kit.postValue(kitDao.getKitById(kitId));
            }
        };
        diskIOExecutor.execute(runnable);
    }
    public void getAllKits(MutableLiveData<List<Kit>> kitList){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                kitList.postValue(kitDao.getAll());
            }
        };
        diskIOExecutor.execute(runnable);
    }
    public void getAllCellsByKit(Kit kit, MutableLiveData<List<Cell>> cellList){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cellList.postValue(cellDao.getCellsByKitId(kit.id));
            }
        };
        diskIOExecutor.execute(runnable);
    }

    public void removeCell(Cell cell){
        Runnable removeCellRnd = new Runnable() {
            @Override
            public void run() {
                cellDao.deleteCell(cell);
            }
        };
        diskIOExecutor.execute(removeCellRnd);
    }

    public void insertSession(Session session){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sessionDao.insertSession(session);
            }
        };
        diskIOExecutor.execute(runnable);
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

