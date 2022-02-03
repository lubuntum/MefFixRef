package database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCell(Cell cell);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertKit(Kit kit);
    @Transaction
    public void insert(Kit kit, List<Cell> cells){
        final long kitId = insertKit(kit);
        for (Cell cell : cells) {
            cell.kitId = kitId;
            insertCell(cell);
        }
    }

    @Update
    public abstract int updateKit(Kit kit);//каскадное должно быть
    @Update
    public abstract void  updateCells(List<Cell> cells);
    @Transaction
    public void update(Kit kit,List<Cell> cells){
        updateKit(kit);
        updateCells(cells);
    }

    @Query("SELECT * FROM kit WHERE kit_name == :kitName")
    public abstract Kit getKit(String kitName);

    @Delete
    public abstract void deleteCell(Cell cell);


}
