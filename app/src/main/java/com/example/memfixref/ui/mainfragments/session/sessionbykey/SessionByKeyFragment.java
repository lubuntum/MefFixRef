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
import android.widget.Toast;

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

    EditText keyEditText;
    TextView valueTextView;

    BootstrapButton promptBtn;
    BootstrapButton nextSlideBtn;
    BootstrapProgressBar progressBar;
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
        valueTextView = view.findViewById(R.id.valueTextView);
        keyEditText = view.findViewById(R.id.keyEditText);

        promptBtn = view.findViewById(R.id.promptBtn);
        nextSlideBtn = view.findViewById(R.id.nextSlideBtn);
        progressBar = view.findViewById(R.id.progressBarView);

        promptBtn.setOnClickListener((View view1)->{
            String word = keyEditText.getText() + sessionByKeyViewModel.getPrompt();
            keyEditText.setText(word);
        });
        nextSlideBtn.setOnClickListener((View view1)->{
            nextSlide();
        });

        progressBarProcessing();

    }
    private void nextSlide(){
        if (sessionByKeyViewModel.getCurrentCell().key.
                equals(keyEditText.getText().toString())) {
            sessionByKeyViewModel.getSession().correct++;
            sessionByKeyViewModel.setSessionIsRunning(false);
            progressBar.setProgress(0);
            keyEditText.setText("");
        }
        else {
            sessionByKeyViewModel.getSession().incorrect++;
            Toast.makeText(getContext(),
                    getResources().getString(R.string.session_wrong_key),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void progressBarProcessing(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (int currentIndex: sessionByKeyViewModel.cellIndexes) {
                    sessionByKeyViewModel.setSessionIsRunning (true);
                    sessionByKeyViewModel.setCurrentCellByIndex(currentIndex);
                    valueTextView.post(()->{
                        valueTextView.setText(
                                sessionByKeyViewModel.getCurrentCell().value);
                    });
                    while (sessionByKeyViewModel.isSessionIsRunning()){
                        if(progressBar.getProgress() >= 100){//next step
                            progressBar.post(()->{
                                progressBar.setProgress(0);
                            });
                            sessionByKeyViewModel.setSessionIsRunning(false);
                        }
                        else{
                            progressBar.post(()->{
                                if(progressBar.getProgress() < 100)
                                    progressBar.setProgress(progressBar.getProgress()+1);
                            });
                        }
                        android.os.SystemClock.sleep(100);
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
