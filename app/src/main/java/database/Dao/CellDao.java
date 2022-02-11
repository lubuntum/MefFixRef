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

/**
 * Из за one to many поместил сюда некоторые методы из KitDao
 * Поскольку необходимо добавлять в базу транзакцию сразу Kit и List<Cell> вместе
 * все методы связанные с Kit помимо updateKit и insertKit находятся в kitDao
 */
@Dao
public abstract class CellDao {

    @Query("SELECT * FROM cell WHERE kit_id == :kitId")
    public abstract List<Cell> getCellsByKitId(long kitId);

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

    @Delete
    public abstract void deleteCell(Cell cell);

}
