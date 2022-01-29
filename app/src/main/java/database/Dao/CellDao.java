package database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import java.util.List;

import database.entities.Cell;
import database.entities.Kit;

@Dao
public abstract class CellDao {

    @Query("SELECT * FROM cell WHERE kit_id == :kitId")
    abstract List<Cell> getCellsByKitId(int kitId);
    @Insert
    public abstract void insertCell(Cell cell);
    @Insert
    public abstract long insertKit(Kit kit);
    @Transaction
    public void insert(Kit kit, List<Cell> cells){
        final long kitId = insertKit(kit);
        for (Cell cell : cells) {
            cell.kitId = kitId;
            insertCell(cell);
        }
    }

    @Delete
    public abstract void deleteCell(Cell cell);
    @Update
    public abstract void  updateCell(Cell cell);

}
