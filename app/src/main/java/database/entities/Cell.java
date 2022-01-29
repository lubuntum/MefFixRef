package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cell",
        foreignKeys = @ForeignKey(entity = Kit.class,parentColumns = "id", childColumns = "kit_id"),
        indices = @Index({"kit_id"}))
public class Cell {
    public Cell(String key, String value){
        this.value = value;
        this.key = key;
    }
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "kit_id")
    public long kitId;
    @ColumnInfo
    public String key;
    @ColumnInfo
    public String value;
    @ColumnInfo
    public float accuracy;
    @ColumnInfo
    public int correct;
    @ColumnInfo
    public int incorrect;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] image;
}
