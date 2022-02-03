package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "kit",
        indices = {@Index(value = {"kit_name"},unique = true)})
public class Kit implements Serializable {

    public Kit(){
        this.kitName = "";
        cells = new LinkedList<>();
    }
    @Ignore
    public Kit(String kitName){
        this.kitName = kitName;
        cells = new LinkedList<>();
    }
    @Ignore
    public Kit(String kitName,List<Cell> cells){
        this.kitName = kitName;
        this.cells = cells;
    }
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "kit_name")
    public String kitName;
    @ColumnInfo(name = "last_use")
    public String lastUse;
    @ColumnInfo(name = "frequency_use")
    public float frequencyUse;//насколько часто используется данный покет
    @ColumnInfo(name = "rating")//data from server only
    public float Rating;
    @ColumnInfo(name = "creation_date")
    public String creationDate;
    @Ignore
    public List<Cell> cells;

}
