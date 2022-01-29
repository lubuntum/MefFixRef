package database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import database.entities.Session;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM session WHERE kit_id == :kitId")
    List<Session> getAllSessionsByKitId(int kitId);
    @Query("SELECT * FROM session WHERE is_succeed == 1")
    List<Session> getAllSucceedSessions();
    @Insert
    void insertSession(Session session);
}