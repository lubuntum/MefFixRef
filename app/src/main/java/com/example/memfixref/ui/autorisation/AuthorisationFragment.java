package com.example.memfixref.ui.autorisation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.charts.Resource;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;

public class AuthorisationFragment extends DialogFragment {
    public static AuthorisationFragment getInstance(){
        AuthorisationFragment authFrag = new AuthorisationFragment();
        Bundle args = new Bundle();
        authFrag.setArguments(args);

        return authFrag;
    }
    public AuthorisationViewModel authorisationViewModel;
    public Handler mHandler;
    public volatile String lastTag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        authorisationViewModel = new ViewModelProvider(this).get(AuthorisationViewModel.class);
        return inflater.inflate(R.layout.authorisation_fragment, container, false);
        //return super.onCreateView(inflater,container,savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHandler = new Handler(Looper.getMainLooper());

        EditText userNameEditText = view.findViewById(R.id.nameEditText);
        EditText emailEditText = view.findViewById(R.id.emailEditText);
        BootstrapButton submitBtn = view.findViewById(R.id.authorisationBtn);
        CheckBox userDataCheckBox = view.findViewById(R.id.userDataCheckBox);

        EditText tagsEditText = view.findViewById(R.id.tagsEditText);
        TextView tagsTextView = view.findViewById(R.id.tagsTextView);
        BootstrapButton undoBtn = view.findViewById(R.id.undoBtn);

        submitBtn.setOnClickListener(view1 -> {
            authorisationViewModel.setName(userNameEditText.getText().toString());
            authorisationViewModel.setEmail(emailEditText.getText().toString());
            authorisationViewModel.setTags(tagsTextView.getText().toString());
            authorisationViewModel.setDataUsagePermission(userDataCheckBox.isChecked());
            Toast.makeText(getContext(), getResources().getString(R.string.toast_success_authorisation), Toast.LENGTH_SHORT).show();
        });

        tagsViewInit(tagsEditText,tagsTextView, undoBtn);


    }


    public void tagsViewInit(EditText tagsEditText, TextView tagsTextView, BootstrapButton undoBtn){

        undoBtn.setOnClickListener((View v)->{
            String currentTags = tagsTextView.getText().toString();
            if (!currentTags.contains(" ")) tagsTextView.setText("");
            else if (currentTags.length() > 0) {
                currentTags = currentTags.substring(0, currentTags.lastIndexOf(" "));
                tagsTextView.setText(currentTags);
            }
        });

        tagsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String tagsStr = tagsEditText.getText().toString();
                if(tagsStr.matches("[A-Za-zА-яа-я]* ") && tagsStr.length() > 2){
                    tagsStr = "#" + tagsStr.substring(0,1).toUpperCase() +
                            tagsStr.substring(1,tagsStr.length()-1);
                    if (tagsTextView.getText().toString().length() == 0){
                        String finalTagsStr = tagsStr;
                        mHandler.post(()->{
                            tagsTextView.setText(finalTagsStr);
                            tagsEditText.setText("");
                        });
                    }
                    else {
                        String tags = tagsTextView.getText().toString() + " " + tagsStr;
                        mHandler.post(() -> {
                            tagsTextView.setText(tags);
                            tagsEditText.setText("");
                        });
                    }

                }
                else if (tagsStr.matches("^ ")){
                    mHandler.post(()->{
                        tagsEditText.setText("");
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                 Runnable acceptTagsChanges = () -> {
                    /*
                    String [] tags = tagsEditText
                            .getText().toString()
                            .replaceAll("#","")
                            .split("");
                     */

                };
                new Thread(acceptTagsChanges).start();
            }
        });
    }


}
