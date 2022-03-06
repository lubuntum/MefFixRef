package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "session",
        foreignKeys = @ForeignKey(entity = Kit.class, parentColumns = "id",childColumns = "kit_id"),
indices = {@Index(value = {"kit_id"})})
public class Session {
    @PrimaryKey
    public long id;
    @ColumnInfo(name = "kit_id")
    public long kitId;
    @ColumnInfo(name = "use_date")
    public String useDate;
    //лучше следать total,mistakes,right, а success вообще убрать потому что не нам определять рамки успеха...
    @ColumnInfo(name = "is_succeed")
    public boolean isSucceed;//если ratio ниже установленног значения то не успешно(в настройках)
    @ColumnInfo(name = "mistakes_ratio")
    public float mistake_ratio;//отношение правильных и ошибочных Cell
}
