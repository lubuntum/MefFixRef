package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "cell",
        foreignKeys = @ForeignKey(entity = Kit.class,parentColumns = "id", childColumns = "kit_id"))
public class Cell {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "kit_id")
    public int kitId;
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
