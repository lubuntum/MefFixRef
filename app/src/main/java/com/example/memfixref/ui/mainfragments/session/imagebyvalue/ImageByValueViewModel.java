package com.example.memfixref.ui.mainfragments.session.imagebyvalue;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.memfixref.R;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.entities.Cell;
import database.entities.Kit;
import database.entities.Session;
import services.MushIndexes;

public class ImageByValueViewModel extends AndroidViewModel {
    public static final String SESSION_TYPE_IMAGE_BY_VALUE = "image_by_value";
    private Kit kit;
    private Session session;

    private int currentCellIndex;// текущий  индекс для cell
    private Cell currentCell;

    private List<String> valueVariants;//текущие варианты
    private int currentValueIndex;

    public ImageByValueViewModel(@NonNull Application application, Kit kit) {
        super(application);
        this.kit = kit;

        Collections.shuffle(this.kit.cells);

        valueVariants = new LinkedList<>();
    }
    //возвращает следубщуб cell
   public Cell nextCell(){
        if (currentCellIndex >= kit.cells.size()){
            currentCellIndex = 0;
        }
        currentCell = kit.cells.get(currentCellIndex);
        currentCellIndex++;
        return currentCell;
   }

   //возвращает следующее значение для отгадывания
   public String nextValue(){
        if (currentValueIndex >= valueVariants.size()){
            currentValueIndex = 0;
        }
        String value = valueVariants.get(currentValueIndex);
        currentValueIndex++;
        return value;
   }

    /***
     * Заполняем массив новыми вариантами с учетом верного значения cell.value
     * затем перемешивает
     * @param cell
     */
   public void generateVariants(Cell cell){
        Random random = new Random();

        valueVariants.clear();
        valueVariants.add(cell.value);
        valueVariants.add(kit.cells.get(random.nextInt(kit.cells.size())).value);
        valueVariants.add(kit.cells.get(random.nextInt(kit.cells.size())).value);
        Collections.shuffle(valueVariants);
        currentValueIndex = 0;
   }
   public Cell getCurrentCell(){
       return currentCell;
   }

   public int getCurrentCellIndex(){
        return currentCellIndex;
   }

   public Kit getKit(){
        return kit;
   }

   public Session getSession(){
       return session;
   }

   public void resetSession(){
       session = Session.createSession(SESSION_TYPE_IMAGE_BY_VALUE,kit.id);
       session.setKit(new MutableLiveData<>(kit));
       currentCellIndex = 0;
       currentCell = null;
       Collections.shuffle(kit.cells);
   }


}
