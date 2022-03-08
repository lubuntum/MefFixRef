package services;

import java.util.List;
import java.util.Random;

import database.entities.Cell;

public class MushIndexes {
    public int[] getMushIndexes(List<Cell> collection){
        int [] arr = new int[collection.size()];
        Random random = new Random();
        for (int i = 0;i < collection.size();i++)
            arr[i] = i;

        for(int i = arr.length-1;i > 0;i--){
            int randIndex = random.nextInt(i);

            int tempValue = arr[randIndex];
            arr[randIndex] = arr[i];
            arr[i] = tempValue;
        }
        return arr;
    }
}
