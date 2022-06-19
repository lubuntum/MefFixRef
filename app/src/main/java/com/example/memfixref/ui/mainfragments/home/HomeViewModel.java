package com.example.memfixref.ui.mainfragments.home;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;

import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;
import com.example.memfixref.ui.mainfragments.kit.onekitdata.cellist.CellAdapter;
import com.example.memfixref.ui.mainfragments.settings.SettingsViewModel;
import com.example.memfixref.ui.optionslist.OptionsItem;

import java.util.LinkedList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private List<OptionsItem> mainMenuItems;
    private SharedPreferences preferences;
    private KitAdapter kitAdapter;

    public HomeViewModel(Application app) {
        super(app);
        mainMenuInit(app);
        preferences = app.getSharedPreferences(SettingsViewModel.SETTINGS_STORAGE,Context.MODE_PRIVATE);
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

    public KitAdapter getKitAdapter() {
        return kitAdapter;
    }

    public void setKitAdapter(KitAdapter kitAdapter) {
        this.kitAdapter = kitAdapter;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public List<OptionsItem> getMainMenuItems() {
        return mainMenuItems;
    }

}