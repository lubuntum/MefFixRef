package com.example.memfixref.ui.mainfragments.kit.onekitdata;

import android.app.Application;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.memfixref.R;

import java.util.LinkedList;
import java.util.List;

import database.Repository;
import database.entities.Cell;
import database.entities.Kit;
import services.DateFormat;

public class KitViewModel extends AndroidViewModel {
    //private List<Cell> cellList;
    private MutableLiveData<List<Cell>> cellList;
    private Kit kit;
    private ArrayAdapter<Cell> arrayAdapter;
    private Repository repo;
    public KitViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getInstance(application);
        cellList = new MutableLiveData<>();
        /*
        kit = new Kit();
        kit.cells = new LinkedList<>();
        cellList = new MutableLiveData<>();
        cellList.setValue(kit.cells);

         */
        //kit.cells.add(new Cell(String.valueOf(new Random().nextInt(10)),"TestValue"));
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
        if(kit.cells == null) {
            kit.cells = new LinkedList<>();
            //uploadCells();
        }
        else cellList.setValue(kit.cells);
    }

    public void uploadCells(){
        repo.getAllCellsByKit(kit, cellList);
    }
    public void removeCell(Cell cell){
        if (kit.cells.contains(cell)) {
            repo.removeCell(cell);
            kit.cells.remove(cell);
            if (arrayAdapter != null) {
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
    public void saveKit(){
        try {
            if (kit != null) {
                DateFormat dateFormat = new DateFormat();
                kit.creationDate = dateFormat.getCurrentDateWithFormat();
                if (kit.id != 0 ){
                    repo.updateKitWithCells(kit);
                }
                else
                    repo.insertKitWithCells(kit);

            }
        }
        catch (Exception e){
            Toast.makeText(getApplication(),
                    getApplication().getResources().
                            getString(R.string.toast_reload_app),Toast.LENGTH_LONG).show();
        }
    }

    public void updateKit(){
        repo.updateKitWithCells(kit);
    }

    public ArrayAdapter<Cell> getArrayAdapter() {
        return arrayAdapter;
    }

    public void setArrayAdapter(ArrayAdapter<Cell> arrayAdapter) {
        this.arrayAdapter = arrayAdapter;
    }


    public MutableLiveData<List<Cell>> getCellList() {
        return cellList;
    }

}
