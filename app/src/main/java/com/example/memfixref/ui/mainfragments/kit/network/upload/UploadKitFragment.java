package com.example.memfixref.ui.mainfragments.kit.network.upload;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.onekitdata.cellist.CellAdapter;
import com.example.memfixref.ui.mainfragments.settings.SettingsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import database.entities.Kit;
import database.entities.Message;
import database.web.APIKitServices;
import database.web.WebRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadKitFragment extends Fragment {
    public static UploadKitFragment newInstance(Kit kit) {

        Bundle args = new Bundle();
        args.putSerializable("kit",kit);
        UploadKitFragment fragment = new UploadKitFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private UploadKitViewModel uploadKitViewModel;
    private Handler mHandler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHandler = new Handler(Looper.getMainLooper());
        if (getArguments() != null && getArguments().containsKey("kit")){
            Kit kit = (Kit)getArguments().getSerializable("kit");
            uploadKitViewModel = new ViewModelProvider(this,new UploadKitViewModelFactory(getActivity().getApplication(),kit)).get(UploadKitViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_submit_kit,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView userAgreementText = view.findViewById(R.id.userAgreementText);
        CheckBox userAgreementCheckBox = view.findViewById(R.id.userAgreementCheck);

        userAgreementCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) userAgreementText.setTextColor(getResources().getColor(R.color.bootstrap_brand_success));
                else userAgreementText.setTextColor(getResources().getColor(R.color.bootstrap_gray_light));
            }
        });
        TextView kitNameTextView = view.findViewById(R.id.kitNameTextView);
        TextView authorTextView = view.findViewById(R.id.authorTextView);
        TextView cellCountTextView = view.findViewById(R.id.cellCountTextView);

        kitNameTextView.setText(uploadKitViewModel.getKit().kitName);
        authorTextView.setText(uploadKitViewModel.getPreferences().getString(SettingsViewModel.USERNAME,"Unknown"));
        cellCountTextView.setText(String.valueOf(uploadKitViewModel.getKit().cells.size()));

        BootstrapEditText tagsEditText = view.findViewById(R.id.tagsEditText);
        BootstrapEditText descriptionEditText = view.findViewById(R.id.descriptionEditText);
        //Теги с большой буквы
        tagsEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocus) {
                if (!isFocus){
                    List<String> tags = new LinkedList<>( Arrays.asList(tagsEditText.getText().toString().split(" ")));
                    tagsEditText.setText("");
                    if (tags.size() > 0) {
                        for (int i = 0; i < tags.size(); i++) {

                            if (tags.get(i).matches("[A-Za-zА-Яа-я_]{2,12}")) {
                                String tag = tags.get(i).substring(0, 1).toUpperCase() + tags.get(i).substring(1).toLowerCase();
                                tagsEditText.setText(tagsEditText.getText().toString() + tag + " ");
                            }
                        }
                    }
                }
            }
        });

        BootstrapButton submitBtn = view.findViewById(R.id.uploadKitBtn);
        FloatingActionButton submitFloatBtn = view.findViewById(R.id.uploadKitFloatBtn);
        View.OnClickListener submitListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIKitServices apiKitServices = WebRepository
                        .getRetrofitInstance()
                        .create(APIKitServices.class);
                Kit kit = uploadKitViewModel.getKit();
                kit.description = descriptionEditText.getText().toString();
                String [] tags = tagsEditText.getText().toString().split(" ");

                Call<Message> call = apiKitServices.sendKit(kit, tags);
                call.enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (response.body().message == null){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), getResources().getString(R.string.toast_network_trouble), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if (response.body().message.equals("Kit saved")){
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), getResources().getString(R.string.toast_kit_upload), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), getResources().getString(R.string.toast_kit_name_error), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), getResources().getString(R.string.toast_network_trouble), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        };
        submitBtn.setOnClickListener(submitListener);
        submitFloatBtn.setOnClickListener(submitListener);

        cellsListInit(view,savedInstanceState);
    }
    public void cellsListInit(View view, Bundle savedInstanceState){
        ListView listView = view.findViewById(R.id.cellListView);
        uploadKitViewModel.setCellAdapter(
                new CellAdapter(getContext(),R.layout.cell_item,uploadKitViewModel.getKit().cells));
        listView.setAdapter(uploadKitViewModel.getCellAdapter());
    }
}
