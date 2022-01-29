package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "kit",
        indices = {@Index(value = {"kit_name"},unique = true)})
public class Kit {

    @PrimaryKey(autoGenerate = true)
    public int id;
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

    public List<Cell> cells;

}
