package com.example.memfixref;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.example.memfixref.ui.mainfragments.session.SessionPrepareFragment;

public class SessionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        FragmentManager fragmentManager = getSupportFragmentManager();
        SessionPrepareFragment sessionPrepareFragment = SessionPrepareFragment.getInstance();
        fragmentManager.beginTransaction()
                .add(R.id.main_session_fragment,
                sessionPrepareFragment,
                "session_prepare_fragment").commit();
    }
}
