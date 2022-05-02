package com.example.memfixref.ui.mainfragments.kit.onekitdata.cellist;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.memfixref.R;

import java.util.List;

import database.entities.Cell;

public class CellAdapter extends ArrayAdapter<Cell> {
    public int layout;
    public List<Cell> cellList;
    private LayoutInflater inflater;
    public CellAdapter(@NonNull Context context, int layout, @NonNull List<Cell> cellList) {
        super(context, layout, cellList);
        this.cellList = cellList;
        this.layout = layout;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = inflater.inflate(layout,parent,false);


        TextView textView = convertView.findViewById(R.id.cellItemTextView);// just text of the key value
        ImageView imageView = convertView.findViewById(R.id.cellItemImage);
        Cell cell = cellList.get(position);
        String itemText = cell.getKey();

        textView.setText(itemText);
        if (cell.image != null){
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(cell.image, 0, cell.image.length));
        }

        return convertView;
    }
}
