package com.example.memfixref;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.memfixref.ui.mainfragments.kit.onekitdata.KitFragment;

import database.entities.Kit;

public class ChangeKitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_kit_activity);

        Bundle args = getIntent().getExtras();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (args != null && args.containsKey("kit")){
            Fragment kitFragment = KitFragment.newInstance((Kit)args.getSerializable("kit"));
            fragmentManager.beginTransaction()
                    .add(R.id.changeKitMainFragment,kitFragment,"editKit")
                    .commit();
        }
        else {
            fragmentManager.beginTransaction()
                    .add(R.id.changeKitMainFragment, KitFragment.class,null)
                    .commit();
        }
    }
}
