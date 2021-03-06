package database.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import database.entities.Session;

@Dao
public interface SessionDao {
    @Query("SELECT * FROM session WHERE kit_id == :kitId")
    List<Session> getAllSessionsByKitId(long kitId);
    //@Query("SELECT * FROM session WHERE is_succeed == 1")
    //List<Session> getAllSucceedSessions();
    //Все сессии за определенную дату
    @Query("SELECT * FROM session WHERE use_date == :date")
    List<Session> getAllSessionByDate(String date);
    @Query("SELECT * FROM session WHERE kit_id==:kitId AND session_type==:sessionType")
    List<Session> getAllSessionByKitIdAndSessionType(long kitId,String sessionType);

    @Insert
    void insertSession(Session session);
}
