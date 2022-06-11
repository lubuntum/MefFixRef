package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "cell",
        foreignKeys = @ForeignKey(entity = Kit.class,parentColumns = "id", childColumns = "kit_id",
                onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        indices = @Index({"kit_id"}))
public class Cell implements Serializable {
    public Cell(String key, String value){
        this.value = value;
        this.key = key;
    }
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "kit_id")
    public long kitId;
    @SerializedName("key")
    @ColumnInfo
    public String key;
    @SerializedName("value")
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
    @ColumnInfo(name = "record_path")
    public String recordPath;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public int getCorrect() {
        return correct;
    }

    public int getIncorrect() {
        return incorrect;
    }
}
