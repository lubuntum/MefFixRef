package com.example.memfixref.ui.optionslist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.memfixref.ChangeKitActivity;
import com.example.memfixref.MainActivity;
import com.example.memfixref.R;

import java.util.List;

public class OptionsListAdapter extends ArrayAdapter<OptionsItem> {
    private LayoutInflater inflater;
    private int layout;
    private List<OptionsItem> items;

    public OptionsListAdapter(Context context, int res, List<OptionsItem> listItems){
        super(context,res,listItems);
        this.items = listItems;
        this.layout = res;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(this.layout,parent,false);
        }
        ImageView icon = convertView.findViewById(R.id.itemIcon);
        TextView text = convertView.findViewById(R.id.itemText);
        OptionsItem item = items.get(position);
        icon.setImageResource(item.icon);
        text.setText(item.text);

        return convertView;
    }

}
