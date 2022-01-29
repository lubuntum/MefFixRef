package database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

import database.entities.Cell;

@Dao
public interface CellDao {

    @Query("SELECT * FROM cell WHERE kit_id == :kitId")
    List<Cell> getCellsByKitId(int kitId);
    @Insert
    void insertAll(List<Cell> cells);
    @Delete
    void deleteCell(Cell cell);
    @Update
    void  updateCell(Cell cell);

}
