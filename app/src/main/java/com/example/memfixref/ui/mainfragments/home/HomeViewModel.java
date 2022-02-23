package com.example.memfixref.ui.mainfragments.home;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;

import com.example.memfixref.R;
import com.example.memfixref.ui.optionslist.OptionsItem;

import java.util.LinkedList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private List<OptionsItem> mainMenuItems;

    public HomeViewModel(Application app) {
        super(app);
        mainMenuInit(app);
    }

    //старый формат, теперь контейнеры!!
    private void mainMenuInit(Context context){
        if (mainMenuItems == null) {
            mainMenuItems = new LinkedList<>();
            mainMenuItems.add(new OptionsItem(R.drawable.ic_start,
                    context.getResources().getString(R.string.main_menu_item_1)));
            mainMenuItems.add(new OptionsItem(R.drawable.ic_add_new,
                    context.getResources().getString(R.string.main_menu_item_2)));
            mainMenuItems.add(new OptionsItem(R.drawable.ic_network,
                    context.getResources().getString(R.string.main_menu_item_3)));
        }
    }

    public List<OptionsItem> getMainMenuItems() {
        return mainMenuItems;
    }

}