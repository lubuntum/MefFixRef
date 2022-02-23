package com.example.memfixref.ui.mainfragments.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.memfixref.R;

public class SessionPrepareFragment extends Fragment {
    public static SessionPrepareFragment getInstance(){
        Bundle args = new Bundle();
        SessionPrepareFragment sessionPrepareFragment = new SessionPrepareFragment();
        sessionPrepareFragment.setArguments(args);
        return sessionPrepareFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_session_prepare,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout sessionByKeyBtn = view.findViewById(R.id.sessionByKeyContainer);
        LinearLayout sessionByValueBtn = view.findViewById(R.id.sessionByValueContainer);
        LinearLayout sessionByImageBtn = view.findViewById(R.id.sessionByImageContainer);
        sessionByKeyBtn.setOnClickListener((View container)->{
            //Тут так же создать Session сущность для статистики если разрешено
            Toast.makeText(getContext(), "By key", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_session_fragment,
                    SessionByKeyFragment.newInstance(),"session_by_key").
                    addToBackStack(null).
                    commit();

        });
        sessionByValueBtn.setOnClickListener((View container)->{

        });
        sessionByImageBtn.setOnClickListener((View container)->{

        });
    }
}
