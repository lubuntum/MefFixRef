package com.example.memfixref.ui.mainfragments.session.relativelists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.memfixref.R;

import java.util.List;

import database.entities.Cell;

/**
 * Accept cell list, in session need two lists with one same cell list, but by value and by key
 * in first list will be paste key or value in TextView and the opposed with other list.
 * It's important, because in session must be chosen only one object, but from to lists by key and value, and this is
 * efficient way because we just can equal cells from both lists and if it's true then it's same cell
 */
public class RelativeListAdapter extends ArrayAdapter<Cell> {
    public  boolean isValue;

    public List<Cell> cellList;
    public LayoutInflater inflater;
    public int resource;

    public RelativeListAdapter(@NonNull Context context, int resource, @NonNull List<Cell> mushCellList,boolean isValue) {
        super(context, resource, mushCellList);
        this.isValue = isValue;

        this.cellList = mushCellList;
        this.inflater = LayoutInflater.from(getContext());
        this.resource = resource;
    }

    //именно здесь важно что бы каждый новый прокручиваемый эл. списка был новым, уникальным
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.relative_list_item,parent,false);
        TextView itemTextView = view.findViewById(R.id.itemNameTextView);
        if (isValue)
            itemTextView.setText(cellList.get(position).getValue());
        else itemTextView.setText(cellList.get(position).getKey());
        return view;
    }
}
