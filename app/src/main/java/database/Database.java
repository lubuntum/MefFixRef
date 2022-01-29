package database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import database.Dao.CellDao;
import database.Dao.KitDao;
import database.Dao.SessionDao;
import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;


@androidx.room.Database(entities = {Kit.class, Cell.class, Session.class},version = 1)
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME = "AppDatabase";
    public abstract KitDao kitDao();
    public abstract CellDao cellDao();
    public abstract SessionDao sessionDao();
    public static volatile Database INSTANCE = null;

    public static Database getInstance(Context context){
        if(INSTANCE == null){
            synchronized (Database.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,Database.class,DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
