package com.example.memfixref.ui.mainfragments.session.endresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.plots.successsessionplot.SessionSuccessPlotFragment;

import java.text.DecimalFormat;
import java.util.List;

import database.entities.Session;

public class SessionResultFragment extends Fragment {
    SessionResultViewModel sessionResultViewModel;
    public static SessionResultFragment newInstance(Session session) {

        Bundle args = new Bundle();
        args.putSerializable("session",session);
        SessionResultFragment fragment = new SessionResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null && getArguments().containsKey("session")){
            Session session = (Session)getArguments().getSerializable("session");
            //sessionResultViewModel = new ViewModelProvider(this).get(SessionResultViewModel.class);
            sessionResultViewModel = new ViewModelProvider(this, new SessionResultViewModelFactory(
                    getActivity().getApplication(),session)).
                        get(SessionResultViewModel.class);
        }
        //sessionResultViewModel = new ViewModelProvider(this,new SessionResultViewModelFactory(getActivity(),))
        return inflater.inflate(R.layout.fragment_session_end_result,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView resultTextView = view.findViewById(R.id.resultTextView);
        TextView kitNameTextView = view.findViewById(R.id.kitNameTextView);
        TextView promptTextView = view.findViewById(R.id.promptUseTextView);
        TextView totalTextView = view.findViewById(R.id.totalTextView);
        TextView incorrectTextView = view.findViewById(R.id.incorrectTextView);
        TextView correctTextView = view.findViewById(R.id.correctTextView);
        TextView averageResultTextView = view.findViewById(R.id.averageResultTextView);

        String kitNameStr = getResources().getString(R.string.session_end_kit_theme) +
                sessionResultViewModel.getKitName();
        String promptStr = getResources().getString(R.string.session_end_prompt_use) +
                sessionResultViewModel.getSessionPrompt();
        String incorrectStr = getResources().getString(R.string.session_end_mistakes) +
                sessionResultViewModel.getIncorrect();
        String correctStr = getResources().getString(R.string.session_end_right) +
                sessionResultViewModel.getCorrect();

        if (sessionResultViewModel.getSession().prompt == 0)
            resultTextView.setTextColor(getResources().getColor(R.color.bootstrap_brand_success));
        kitNameTextView.setText(kitNameStr);
        promptTextView.setText(promptStr);
        //totalTextView.setText(String.valueOf(sessionResultViewModel.getSession().));
        incorrectTextView.setText(incorrectStr);
        correctTextView.setText(correctStr);

        //Для подсчета среднего значения по успеваемости
        Observer<List<Session>> sessionsObserver = new Observer<List<Session>>() {
            @Override
            public void onChanged(List<Session> sessions) {
                int correct = 0;
                int general = 0;
                for (Session session: sessions) {
                    correct += session.correct;
                    general += session.correct + session.incorrect;
                }
                if (general != 0){
                    DecimalFormat format = new DecimalFormat("###.##");
                    String averageResultStr = getResources().getString(R.string.session_end_average_result) +
                            format.format((double)correct/general);
                    averageResultTextView.setText(averageResultStr);

                }
            }
        };

        //закрепление наблюдателей
        sessionResultViewModel.getSessionList().
                observe(getViewLifecycleOwner(),sessionsObserver);

        BootstrapButton repeatBtn = view.findViewById(R.id.repeatBtn);
        BootstrapButton backBtn = view.findViewById(R.id.backBtn);

        //getParentFragmentManager().beginTransaction().remove(this).commit();
        //getParentFragmentManager().popBackStack("session_prepare",
        //        FragmentManager.POP_BACK_STACK_INCLUSIVE);
        repeatBtn.setOnClickListener((View view1)->{
            getParentFragmentManager().popBackStack();
        });
        backBtn.setOnClickListener((View view1)->{
            getParentFragmentManager().
                    popBackStackImmediate("session_prepare_trans",FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });

        getParentFragmentManager().beginTransaction()
                .add(R.id.FragmentSessionSuccessPlot,
                        SessionSuccessPlotFragment.newInstance(
                                sessionResultViewModel.getKit()
                                ,sessionResultViewModel.getSession().sessionType),
                        "session_plot").
                commit();

    }


    @Override
    public void onStop() {
        super.onStop();
        //getParentFragmentManager().beginTransaction().remove(this).commit();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //при нажатии кнопки back он убирает прошлый фрагмент а этот игнорирует ! и поэтому он остается на экране!
    }
}
