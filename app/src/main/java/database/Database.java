package database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

    /*
    public static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE session " +
                    "ADD COLUMN incorrect INTEGER DEFAULT 0 NOT NULL;");
            database.execSQL("ALTER TABLE session " +
                    "ADD COLUMN correct INTEGER DEFAULT 0 NOT NULL;");
            database.execSQL("ALTER TABLE session " +
                    "ADD COLUMN prompt INTEGER DEFAULT 0 NOT NULL;");
            database.execSQL("ALTER TABLE session " +
                    "ADD COLUMN total_time INTEGER DEFAULT 0 NOT NULL;");
        }
    };
    public static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.beginTransaction();
            database.execSQL("ALTER TABLE session RENAME TO session_old");
            database.execSQL("CREATE TABLE session (" +
                    "id INTEGER primary key autoincrement NOT NULL," +
                    "kit_id integer NOT NULL REFERENCES kit(id))");
            database.endTransaction();
        }
    };
     */


    public static Database getInstance(Context context){
        if(INSTANCE == null){
            synchronized (Database.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,Database.class,DATABASE_NAME)
                            //.addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
