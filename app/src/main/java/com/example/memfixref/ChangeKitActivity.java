package com.example.memfixref;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.memfixref.ui.mainfragments.kit.onekitdata.KitFragment;

public class ChangeKitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_kit_activity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.changeKitMainFragment, KitFragment.class,null)
                .commit();
    }
}
