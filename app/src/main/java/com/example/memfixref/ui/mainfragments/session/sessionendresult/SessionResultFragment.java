package com.example.memfixref.ui.mainfragments.session.sessionendresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.session.SessionPrepareFragment;
import com.example.memfixref.ui.mainfragments.session.sessionbykey.SessionByKeyFragment;

import org.w3c.dom.Text;

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
            Session session = (Session)getArguments().getParcelable("session");
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
        resultTextView.setTextColor(getResources().getColor(R.color.bootstrap_brand_success));
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