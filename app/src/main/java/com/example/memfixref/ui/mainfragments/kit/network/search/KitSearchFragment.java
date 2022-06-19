package com.example.memfixref.ui.mainfragments.kit.network.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.ChangeKitActivity;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import database.entities.Kit;
import database.web.APIKitServices;
import database.web.WebRepository;
import database.web.jsondeserializer.KitDeserializer;
import database.web.jsondeserializer.KitListDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitSearchFragment extends Fragment {
    private KitSearchViewModel kitSearchViewModel;
    private Handler mHandler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        kitSearchViewModel = new ViewModelProvider(this).get(KitSearchViewModel.class);
        mHandler = new Handler(getActivity().getMainLooper());
        return inflater.inflate(R.layout.fragment_network_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BootstrapButton searchBtn = view.findViewById(R.id.searchBtn);
        EditText tagEditText = view.findViewById(R.id.findByTagEditText);
        ListView kitListView = view.findViewById(R.id.kitListView);
        kitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Kit kitFromAdapter = kitSearchViewModel.getKitAdapter().kitList.get(i);
                //Если cell еще не прогружены
                if (kitFromAdapter.cells == null) {
                    APIKitServices apiKitServices = WebRepository
                            .getRetrofitInstanceWithConverter(Kit.class, new KitDeserializer())
                            .create(APIKitServices.class);
                    Call<Kit> call = apiKitServices.getKitWithCellsByName(kitFromAdapter.kitName);
                    call.enqueue(new Callback<Kit>() {
                        @Override
                        public void onResponse(Call<Kit> call, Response<Kit> response) {
                            if (response.body() == null) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), getResources().getString(R.string.toast_network_cell), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Kit kit = response.body();
                                kitFromAdapter.cells = kit.cells;
                                Intent intent = new Intent(getContext(), ChangeKitActivity.class);
                                intent.putExtra("kit", kit);
                                getActivity().startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<Kit> call, Throwable t) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), getResources().getString(R.string.toast_network_trouble), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
                else {
                    Intent intent = new Intent(getContext(), ChangeKitActivity.class);
                    intent.putExtra("kit", kitFromAdapter);
                    getActivity().startActivity(intent);
                }
            }
        });
        //Получаем все теги и делаем запрос
        searchBtn.setOnClickListener((View v)->{
            List<String> tags = Arrays.asList(tagEditText.getText().toString().split(" "));
            HashMap<String,String> hashMap = new HashMap<>();
            for(int i = 0; i < tags.size();i++){
                hashMap.put(String.valueOf(i),tags.get(i));
            }
            APIKitServices apiKitServices = WebRepository
                    .getRetrofitInstanceWithConverter(new TypeToken<List<Kit>>(){}.getType(),new KitListDeserializer())
                    .create(APIKitServices.class);
            Call<List<Kit>> call = apiKitServices.getKitListByTags(hashMap);
            call.enqueue(new Callback<List<Kit>>() {
                @Override
                public void onResponse(Call<List<Kit>> call, Response<List<Kit>> response) {
                    if (response.body() == null || response.body().size() == 0){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                String tags = tagEditText.getText().toString();
                                Toast.makeText(getContext(),String.format(getResources().getString(R.string.toast_network_empty),tags), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    if (response.body() != null) {
                        kitSearchViewModel.getKits().postValue(response.body());
                        //kitSearchViewModel.setKitAdapter(new KitAdapter(getContext(),R.layout.kit_item,response.body()));
                    }
                }

                @Override
                public void onFailure(Call<List<Kit>> call, Throwable t) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), getResources().getString(R.string.toast_network_trouble), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        });
        Observer<List<Kit>> kitListObserver = new Observer<List<Kit>>() {
            @Override
            public void onChanged(List<Kit> kits) {
                kitSearchViewModel.setKitAdapter(new KitAdapter(getContext(),R.layout.kit_item,kits));
                kitListView.setAdapter(kitSearchViewModel.getKitAdapter());
                kitSearchViewModel.getKitAdapter().notifyDataSetChanged();
            }
        };
        kitSearchViewModel.getKits().observe(getViewLifecycleOwner(),kitListObserver);
    }
}
