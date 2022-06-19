package com.example.memfixref.ui.mainfragments.kit.network.kitlistbytags;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.ChangeKitActivity;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;

import java.util.LinkedList;
import java.util.List;

import database.entities.Kit;
import database.web.APIKitServices;
import database.web.WebRepository;
import database.web.jsondeserializer.KitDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KitListByTagsFragment extends Fragment {

    public static KitListByTagsFragment newInstance(List<Kit> kits) {

        Bundle args = new Bundle();
        args.putInt("size",kits.size());
        for(int i = 0; i < kits.size();i++)
            args.putSerializable(String.valueOf(i),kits.get(i));

        KitListByTagsFragment fragment = new KitListByTagsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public KitListByTagsViewModel kitListByTagsViewModel;
    public Handler mHandler;
    public AdapterView.OnItemClickListener kitListItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Kit kitFromAdapter = kitListByTagsViewModel.getKitAdapter().kitList.get(i);
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
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments().containsKey("size") && getArguments().getInt("size") > 0){
            List<Kit> kits = new LinkedList<>();
            for (int i = 0; i < getArguments().getInt("size");i++){
                kits.add((Kit)getArguments().getSerializable(String.valueOf(i)));
            }
            kitListByTagsViewModel = new ViewModelProvider(
                    this,
                    new KitListByTagsViewModelFactory(kits)).get(KitListByTagsViewModel.class);
        }
        mHandler = new Handler(Looper.getMainLooper());
        return inflater.inflate(R.layout.fragment_kit_list_by_tags,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.kitListByTag);
        kitListByTagsViewModel.setKitAdapter(new KitAdapter(getContext(), R.layout.kit_item,kitListByTagsViewModel.getKits()));
        listView.setAdapter(kitListByTagsViewModel.getKitAdapter());
        listView.setOnItemClickListener(kitListItemListener);

    }

}
