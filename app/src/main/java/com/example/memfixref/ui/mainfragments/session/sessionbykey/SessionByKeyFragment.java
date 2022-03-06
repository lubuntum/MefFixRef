package com.example.memfixref.ui.mainfragments.session.sessionbykey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.session.SessionPrepareViewModel;

public class SessionByKeyFragment extends Fragment {
    SessionByKeyViewModel sessionByKeyViewModel;
    public static SessionByKeyFragment newInstance() {
        Bundle args = new Bundle();
        SessionByKeyFragment fragment = new SessionByKeyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //берем выбранный kit из viewModel и передаем его текущей сессионной viewModel как входящий парам.
        SessionPrepareViewModel sessionPrepareViewModel = new ViewModelProvider(getActivity()).get(SessionPrepareViewModel.class);
        sessionByKeyViewModel = new ViewModelProvider(this,
                new SessionByKeyViewModelFactory(sessionPrepareViewModel.getPickedKit().getValue())).
                get(SessionByKeyViewModel.class);
        return inflater.inflate(R.layout.fragment_session_by_key,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView valueTextView = view.findViewById(R.id.valueTextView);
        EditText keyEditText = view.findViewById(R.id.keyEditText);

        BootstrapButton promptBtn = view.findViewById(R.id.promptBtn);
        BootstrapButton nextSlideBtn = view.findViewById(R.id.nextSlideBtn);
        BootstrapProgressBar progressBar = view.findViewById(R.id.progressBarView);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(progressBar.getProgress() >=100){
                        progressBar.setProgress(0);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.post(()->{
                        if(progressBar.getProgress() < 100)
                            progressBar.setProgress(progressBar.getProgress()+10);
                    });
                }
            }
        };
        Thread progressBarThread = new Thread(runnable);
        progressBarThread.start();



    }
}
