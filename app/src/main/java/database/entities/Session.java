package database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "session")//дописать внешний ключ
public class Session {
    @ColumnInfo
    public int id;
    @ColumnInfo(name = "kit_id")
    public int kitId;
    @ColumnInfo(name = "use_date")
    public String useDate;
    @ColumnInfo(name = "is_succeed")
    public boolean isSucceed;//если ratio ниже установленног значения то не успешно(в настройках)
    @ColumnInfo(name = "mistakes_ratio")
    public float mistake_ratio;//отношение правильных и ошибочных Cell
}
