package com.example.memfixref.ui.mainfragments.session.sessionbykey;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.session.SessionPrepareViewModel;
import com.example.memfixref.ui.mainfragments.session.sessionendresult.SessionResultFragment;

import database.entities.Cell;

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

                for (int currentIndex: sessionByKeyViewModel.cellIndexes) {
                    boolean sessionIsRunning = true;
                    valueTextView.post(()->{
                        valueTextView.setText(
                                sessionByKeyViewModel.kit.cells.get(currentIndex).value);
                    });
                    while (sessionIsRunning){
                        if(progressBar.getProgress() >=100){//next step
                            progressBar.post(()->{
                                progressBar.setProgress(0);
                            });
                        sessionIsRunning = false;
                        }
                        else{
                            progressBar.post(()->{
                                if(progressBar.getProgress() < 100)
                                    progressBar.setProgress(progressBar.getProgress()+10);
                            });
                        }
                        android.os.SystemClock.sleep(500);
                    }

                }
                //под конец обучения вызвать фрагмент с результатами(передаются все данные о сессии)
                //не передаю через viewmodel потому что текущая viewmodel сущ. в рамках данного фрагмента
                //и далее к ней нельзя будет обратиться поскольку ее не будет, тут все изолировано
                // ведь это единичная игровая сессия
                FragmentManager fragmentManager = getParentFragmentManager();

                //getParentFragmentManager().beginTransaction().remove(getCurrentFragment()).commit();
                fragmentManager.beginTransaction().
                        setReorderingAllowed(true).
                        setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).
                        replace(R.id.main_session_fragment,
                                SessionResultFragment.newInstance(sessionByKeyViewModel.getSession()),"session_result").
                        addToBackStack(null).
                        commit();

                //закончить данную сессию и показать результаты в SessionResultViewMode
            }
        };
        Thread progressBarThread = new Thread(runnable);
        progressBarThread.start();

    }
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public Fragment getCurrentFragment(){
        return this;
    }
}
