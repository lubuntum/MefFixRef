package database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import database.entities.Kit;

@Dao
public interface KitDao {
    @Query("SELECT * FROM kit")
    List<Kit> getAll();
    //Получить все пакеты с большим или равным коэфиценту использования чем входящий
    //полезно для списка часто используемых
    @Query("SELECT * FROM kit WHERE frequency_use >= :ratio")
    List<Kit> getMostPopular(float ratio);
    //Поиск по указанному шаблону(имени пакета)
    @Query("SELECT * FROM kit WHERE kit_name LIKE :kitName")
    Kit getKitByName(String kitName);

    @Insert
    void insertAll(List<Kit> kits);

    @Delete
    void deleteKit(Kit kit);

    @Update
    void updateKit(Kit... kit);




}
