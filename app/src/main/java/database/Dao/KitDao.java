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
    //Insert находится в CellDao поскольку проиходит вставка отношения one to many, так очень удобно
    @Query("SELECT * FROM kit")
    List<Kit> getAll();
    //Получить все пакеты с большим или равным коэфиценту использования чем входящий
    //полезно для списка часто используемых
    @Query("SELECT * FROM kit WHERE frequency_use >= :ratio")
    List<Kit> getMostPopular(float ratio);
    //Поиск по указанному шаблону(имени пакета)
    @Query("SELECT * FROM kit WHERE kit_name LIKE :kitName")
    Kit getKitByName(String kitName);

    @Query("SELECT * FROM kit WHERE id LIKE :kitId")
    Kit getKitById(long kitId);

    @Delete
    void deleteKit(Kit kit);

    @Update
    void updateKit(Kit... kit);

}
