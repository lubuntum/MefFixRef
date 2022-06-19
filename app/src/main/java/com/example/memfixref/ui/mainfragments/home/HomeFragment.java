package com.example.memfixref.ui.mainfragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.memfixref.ChangeKitActivity;
import com.example.memfixref.MainActivity;
import com.example.memfixref.NetworkSearchActivity;
import com.example.memfixref.R;
import com.example.memfixref.SessionActivity;
import com.example.memfixref.ui.mainfragments.kit.kitlist.KitAdapter;
import com.example.memfixref.ui.mainfragments.kit.network.kitlistbytags.KitListByTagsFragment;
import com.example.memfixref.ui.mainfragments.kit.onekitdata.cellist.CellAdapter;
import com.example.memfixref.ui.mainfragments.settings.SettingsViewModel;
import com.example.memfixref.ui.optionslist.OptionsListAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import database.entities.Kit;
import database.web.APIKitServices;
import database.web.WebRepository;
import database.web.jsondeserializer.KitListDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * HomeFragment & HomeViewModel жуткое легаси с устаревшим кодом
 * желательно переписать, сделать интерфейс с помощью контейнеров
 * и прописать им события клика, убрать адаптер, вынести работу с контекстом
 * из ViewModel, код очень старый поэтому он такой, ну что поделать,
 * все мы не без греха и все мы учимся и потом смеемся и плачем
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Handler mHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mHandler = new Handler(Looper.getMainLooper());

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Плохо сделано, > 3 часов на рефакторинг в нормальный UI
        ListView mainMenu = view.findViewById(R.id.mainMenuListView);
        OptionsListAdapter optionsListAdapter =
                new OptionsListAdapter(getContext(),R.layout.options_list_item,homeViewModel.getMainMenuItems());
        mainMenu.setAdapter(optionsListAdapter);
        //действия всех опций меню
        mainMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Toast.makeText(getContext(),"Game",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), SessionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    getActivity().startActivity(intent);
                }
                else if (position == 1){
                    Intent intent = new Intent(getContext(), ChangeKitActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    getActivity().startActivity(intent);
                }
                else if (position == 2){
                    Intent intent = new Intent(getContext(), NetworkSearchActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    getActivity().startActivity(intent);
                }
            }
        });

        preferencesListViewInit(view,savedInstanceState);

    }
    public void preferencesListViewInit(View view, Bundle SavedInstanceState){
        //ListView kitByPreferencesListView = view.findViewById(R.id.preferencesListView);
        TextView frequentlyUsedTextView = view.findViewById(R.id.frequentlyUsedTextView);
        APIKitServices apiKitServices = WebRepository
                .getRetrofitInstanceWithConverter(
                        new TypeToken<List<Kit>>(){}.getType(),
                        new KitListDeserializer())
                .create(APIKitServices.class);
        //берется случайный тег из предпочитаемых пользователем
        //идет поиск наборов по данному тегу
        HashMap<String,String> tagsHash = new HashMap<>();
        String [] favoriteTags = homeViewModel.getPreferences().getString(SettingsViewModel.FAVORITE_TAGS,"Language ").split(" ");
        Random random = new Random();
        if(favoriteTags.length > 0) {
            tagsHash.put("0", favoriteTags[random.nextInt(favoriteTags.length - 1)]);
            Call<List<Kit>> call = apiKitServices.getKitListByTags(tagsHash);
            call.enqueue(new Callback<List<Kit>>() {
                @Override
                public void onResponse(Call<List<Kit>> call, Response<List<Kit>> response) {
                    mHandler.post(() -> {
                        List<Kit> kitsByFavoriteTag = response.body();
                        if (kitsByFavoriteTag != null && kitsByFavoriteTag.size() > 0) {
                            /*
                            homeViewModel.setKitAdapter(new KitAdapter(getContext(), R.layout.kit_item, kitsByFavoriteTag));
                            kitByPreferencesListView.setAdapter(homeViewModel.getKitAdapter());
                            homeViewModel.getKitAdapter().notifyDataSetChanged();
                             */
                            frequentlyUsedTextView.setText(frequentlyUsedTextView.getText().toString() + " " + tagsHash.get("0"));
                            FragmentManager fragmentManager = getChildFragmentManager();
                            fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.kitListByTagFragment, KitListByTagsFragment.newInstance(kitsByFavoriteTag),"KitListByTags")
                                    .addToBackStack("KitListByTagsFragment").commit();
                        }
                        else
                            Toast.makeText(getContext(), String.format(getResources().getString(R.string.toast_cant_find_favorite_tags), tagsHash.get("0")), Toast.LENGTH_SHORT).show();
                    });
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
        }
        else Toast.makeText(getContext(), getResources().getString(R.string.toast_add_favorite_tags), Toast.LENGTH_SHORT).show();
    }
}