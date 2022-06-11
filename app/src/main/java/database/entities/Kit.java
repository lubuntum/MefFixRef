package database.entities;

import androidx.lifecycle.MutableLiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("kit_name")
    @ColumnInfo(name = "kit_name")
    public String kitName;
    @ColumnInfo(name = "last_use")
    public String lastUse;
    @ColumnInfo(name = "frequency_use")
    public float frequencyUse;//насколько часто используется данный покет
    @ColumnInfo(name = "rating")//data from server only
    public float Rating;
    @SerializedName("creation_date")
    @ColumnInfo(name = "creation_date")
    public String creationDate;
    //Старый формат используется в onekitdata.KitViewModel
    //Из за того что вначале использовал просто список без Mutable придется оставить
    @Ignore
    public List<Cell> cells;
    @Ignore
    public MutableLiveData<List<Session>> sessionList;
    //Новый формат удобнее, лучше и по SOLID
    //@Ignore
    //public MutableLiveData<List<Cell>> cellsList;
    public boolean isCellsWithImage(){
        if(!cells.isEmpty()){
            for (Cell cell: cells)
                if (cell.image == null || cell.image.length == 0) return false;
        }
        else return false;
        return true;
    }

}
