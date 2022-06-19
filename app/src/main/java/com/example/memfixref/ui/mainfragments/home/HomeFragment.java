package com.example.memfixref.ui.mainfragments.home;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.memfixref.MainActivity;
import com.example.memfixref.NetworkSearchActivity;
import com.example.memfixref.R;
import com.example.memfixref.SessionActivity;
import com.example.memfixref.ui.optionslist.OptionsListAdapter;

/***
 * HomeFragment & HomeViewModel жуткое легаси с устаревшим кодом
 * желательно переписать, сделать интерфейс с помощью контейнеров
 * и прописать им события клика, убрать адаптер, вынести работу с контекстом
 * из ViewModel, код очень старый поэтому он такой, ну что поделать,
 * все мы не без греха и все мы учимся и потом смеемся и плачем
 */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    }
}