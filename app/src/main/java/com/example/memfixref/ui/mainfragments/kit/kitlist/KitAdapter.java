package com.example.memfixref.ui.mainfragments.kit.kitlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.memfixref.R;

import java.util.List;

import database.entities.Kit;

public class KitAdapter extends ArrayAdapter<Kit> {

    public List<Kit> kitList;

    private int layout;
    private LayoutInflater inflater;

    public KitAdapter(@NonNull Context context, int layout, @NonNull List<Kit> kitsList) {
        super(context, layout, kitsList);
        this.kitList = kitsList;
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(layout,parent,false);
        }
        TextView kitName = convertView.findViewById(R.id.kitNameTextView);
        TextView totalUsage = convertView.findViewById(R.id.totalUsageTextView);
        TextView  lastUsageDate = convertView.findViewById(R.id.lastUsageDateTextView);
        Kit kitItem = kitList.get(position);

        kitName.setText(kitItem.kitName);

        String totalUsageStr = getContext().getResources().getString(R.string.total)+ " " + kitItem.frequencyUse;
        totalUsage.setText(totalUsageStr);

        String lastUsageDateStr = getContext().getResources().getString(R.string.last_use) +" "+ kitItem.lastUse;
        lastUsageDate.setText(lastUsageDateStr);

        return convertView;
    }
}
