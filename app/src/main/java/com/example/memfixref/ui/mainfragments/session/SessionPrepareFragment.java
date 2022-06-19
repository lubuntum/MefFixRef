package com.example.memfixref.ui.mainfragments.session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.core.annotations.Line;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.onekitdata.cellist.CellAdapter;
import com.example.memfixref.ui.mainfragments.session.imagebyvalue.ImageByValueFragment;
import com.example.memfixref.ui.mainfragments.session.relativelists.SessionRelativeListsFragment;
import com.example.memfixref.ui.mainfragments.session.sessionselectkit.SessionSelectKitFragment;
import com.example.memfixref.ui.mainfragments.session.bykey.SessionByKeyFragment;

import java.time.format.DateTimeFormatter;
import java.util.List;

import database.entities.Cell;
import database.entities.Kit;
import services.DateFormat;

public class SessionPrepareFragment extends Fragment {
    SessionPrepareViewModel sessionViewModel;
    public static SessionPrepareFragment getInstance(){
        Bundle args = new Bundle();
        SessionPrepareFragment sessionPrepareFragment = new SessionPrepareFragment();
        sessionPrepareFragment.setArguments(args);
        return sessionPrepareFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sessionViewModel = new ViewModelProvider(getActivity()).get(SessionPrepareViewModel.class);
        return inflater.inflate(R.layout.fragment_session_prepare,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout sessionByKeyBtn = view.findViewById(R.id.sessionByKeyContainer);
        LinearLayout sessionByValueBtn = view.findViewById(R.id.sessionByValueContainer);
        LinearLayout sessionByRelativeListsBtn = view.findViewById(R.id.sessionByRelativeListsContainer);
        LinearLayout sessionByImageValueBtn = view.findViewById(R.id.sessionImageByValue);

        LinearLayout chooseKitBtn = view.findViewById(R.id.chooseKitContainer);

        TextView kitNameTextView = view.findViewById(R.id.kitNameTextView);
        ListView listView = view.findViewById(R.id.currentCellsListView);

        sessionByImageValueBtn.setOnClickListener((View container)->{
            if (!isKitEmpty()) {
                if (sessionViewModel.getPickedKit().getValue().isCellsWithImage()) {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().setReorderingAllowed(true).
                            setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).
                            replace(R.id.main_session_fragment,
                                    ImageByValueFragment.newInstance(
                                            sessionViewModel.getPickedKit().getValue()),"session_prepare_fragment").
                            addToBackStack("session_prepare_trans").
                            commit();

                }
                else Toast.makeText(getContext(),
                        getResources().getString(R.string.toast_empty_image),
                        Toast.LENGTH_LONG).show();
            }
        });
        sessionByKeyBtn.setOnClickListener((View container)->{
            //Тут так же создать Session сущность для статистики если разрешено

            //Kit передается внутри фрагмента используя текущую ViewModel зависимый фрагмент исправить
            if (!isKitEmpty()) {
                Toast.makeText(getContext(), "By key", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getParentFragmentManager();

                fragmentManager.beginTransaction().setReorderingAllowed(true).
                        setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).
                        replace(R.id.main_session_fragment,
                                SessionByKeyFragment.newInstance(), "session_prepare_fragment").
                        addToBackStack("session_prepare_trans").
                        commit();
            }



        });
        sessionByValueBtn.setOnClickListener((View container)->{

        });


        sessionByRelativeListsBtn.setOnClickListener((View container)->{

            //kit передается с newInstance, независимый фрагмент новая версия
            if (!isKitEmpty()) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.main_session_fragment,
                                SessionRelativeListsFragment.newInstance(sessionViewModel.getPickedKit().getValue()),
                                "session_prepare_fragment")
                        .addToBackStack("session_prepare_trans")
                        .commit();
            }

        });

        chooseKitBtn.setOnClickListener((View container)->{
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().
                    setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).
                    replace(R.id.main_session_fragment,
                    SessionSelectKitFragment.getInstance(),"session_select_kit").
                    addToBackStack(null).
                    commit();
        });

        Observer<List<Cell>> cellListObserver = new Observer<List<Cell>>() {
            @Override
            public void onChanged(List<Cell> cells) {
                sessionViewModel.setCellAdapter(new CellAdapter(getContext(),R.layout.cell_item,cells));
                sessionViewModel.getPickedKit().getValue().cells = cells;//Solid Kit
                listView.setAdapter(sessionViewModel.getCellAdapter());
            }
        };
        Observer<Kit> kitObserver = new Observer<Kit>() {
            @Override
            public void onChanged(Kit kit) {
                kitNameTextView.setText(kit.kitName);

                DateFormat dateFormat = new DateFormat();
                kit.lastUse = dateFormat.getCurrentDateWithFormat();
                kit.frequencyUse++;
                sessionViewModel.updateKit(kit);
            }
        };
        sessionViewModel.getPickedKit().observe(getViewLifecycleOwner(),kitObserver);
        sessionViewModel.getCellList().observe(getViewLifecycleOwner(),cellListObserver);
    }

    public boolean isKitEmpty(){
        if (sessionViewModel.getPickedKit().getValue() == null){
            Toast.makeText(getContext(),
                    getResources().getString(R.string.empty_kit),
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
