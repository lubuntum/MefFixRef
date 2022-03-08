package com.example.memfixref.ui.mainfragments.session.sessionendresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.R;

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
    }
}
