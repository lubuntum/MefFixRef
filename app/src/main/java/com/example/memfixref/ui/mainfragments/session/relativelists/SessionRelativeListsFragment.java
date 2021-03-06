package com.example.memfixref.ui.mainfragments.session.relativelists;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapProgressBar;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.session.endresult.SessionResultFragment;

import database.entities.Cell;
import database.entities.Kit;

public class SessionRelativeListsFragment extends Fragment {
    public static SessionRelativeListsFragment newInstance(Kit kit) {

        Bundle args = new Bundle();
        args.putSerializable("kit",kit);
        SessionRelativeListsFragment fragment = new SessionRelativeListsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public SessionRelativeListsViewModel relativeListsViewModel;
    private Handler mHandler;

    private ListView keyListView;
    private ListView valueListView;
    private AdapterView.OnItemClickListener keyListViewListener;
    private AdapterView.OnItemClickListener valueListViewListener;
    private View pickedViewByKey;
    private View pickedViewByValue;

    private BootstrapButton endSessionBtn;
    private BootstrapButton promptBtn;

    private BootstrapProgressBar progressBar;

    private final Runnable fadeOutAnimation = new Runnable() {
        @Override
        public void run() {
            mHandler.post(()->{
                keyListView.setOnItemClickListener(null);
                valueListView.setOnItemClickListener(null);
                Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fade);
                pickedViewByKey.setAnimation(animation);
                pickedViewByValue.setAnimation(animation);
            });

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.post(() ->{
                removePickedElements();

                keyListView.setOnItemClickListener(keyListViewListener);
                valueListView.setOnItemClickListener(valueListViewListener);
            });
        }
    };
    private final Runnable wrongCellsAnimation = new Runnable() {
        @Override
        public void run() {
            Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fade_half);
            mHandler.post(() -> {
                //???????????????????? ?????????????? ???? ?????????? ????????????????
                keyListView.setOnItemClickListener(null);
                valueListView.setOnItemClickListener(null);
                pickedViewByKey.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_danger));
                pickedViewByValue.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_danger));
                pickedViewByKey.setAnimation(animation);
                pickedViewByValue.setAnimation(animation);
            });
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.post(() -> {
                pickedViewByKey.setBackground(getResources().getDrawable(R.drawable.gray_borders));
                pickedViewByKey = null;
                pickedViewByValue.setBackground(getResources().getDrawable(R.drawable.gray_borders));
                pickedViewByValue = null;
                relativeListsViewModel.resetPickedCells();

                keyListView.setOnItemClickListener(keyListViewListener);
                valueListView.setOnItemClickListener(valueListViewListener);
            });

        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHandler = new Handler(Looper.getMainLooper());
        if(getArguments() != null && getArguments().containsKey("kit")){
            Kit kit = (Kit)getArguments().getSerializable("kit");
            relativeListsViewModel =
                    new ViewModelProvider(this,
                            new SessionRelativeListsViewModelFactory(getActivity().getApplication(),kit))
                            .get(SessionRelativeListsViewModel.class);
            relativeListsViewModel.createSession();
            relativeListsViewModel.adaptersInit();
        }

        return inflater.inflate(R.layout.fragment_relative_lists_session,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        keyListView = view.findViewById(R.id.keyListView);
        valueListView = view.findViewById(R.id.valueListView);

        endSessionBtn = view.findViewById(R.id.terminateBtn);
        promptBtn = view.findViewById(R.id.hintBtn);

        progressBar = view.findViewById(R.id.progressBarView);
        progressBar.setProgress(0);

        keyListView.setAdapter(relativeListsViewModel.getAdapterByKey());
        valueListView.setAdapter(relativeListsViewModel.getAdapterByValue());

        keyListViewListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (pickedViewByKey != null && pickedViewByKey.equals(view)){
                    pickedViewByKey.setBackground(getResources().getDrawable(R.drawable.gray_borders));
                    pickedViewByKey = null;
                    relativeListsViewModel.setPickedCellFromListByKey(null);
                }
                else {
                    drawPickedViews(view, pickedViewByKey);
                    pickedViewByKey = view;
                    relativeListsViewModel.setPickedCellFromListByKey
                            (relativeListsViewModel.getAdapterByKey().getItem(i));
                }
                checkPickedViews();
            }
        };
        valueListViewListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (pickedViewByValue != null && pickedViewByValue.equals(view)){
                    pickedViewByValue.setBackground(getResources().getDrawable(R.drawable.gray_borders));
                    pickedViewByValue = null;
                    relativeListsViewModel.setPickedCellFromListByValue(null);
                }
                else {
                    drawPickedViews(view, pickedViewByValue);
                    pickedViewByValue = view;
                    relativeListsViewModel.setPickedCellFromListByValue
                            (relativeListsViewModel.getAdapterByValue().getItem(i));
                }
                checkPickedViews();
            }
        };
        //?????????????????? ??????????????
        keyListView.setOnItemClickListener(keyListViewListener);
        valueListView.setOnItemClickListener(valueListViewListener);
        progressBarProcessing();
        sessionBtnInit();
    }
    private void drawPickedViews(View view, View pickedView){
        GradientDrawable gd = new GradientDrawable();
        if (pickedView != null) {
            //gd.setColor(getResources().getColor(R.color.white));
            //gd.setStroke(1,getResources().getColor(R.color.bootstrap_gray_lighter));
            //pickedView.setBackground(gd);
            pickedView.setBackground(getResources().getDrawable(R.drawable.gray_borders));
        }
        view.setBackgroundColor(getResources().getColor(R.color.green_light));
    }
    //???????????????? ?????????????????? UI ?????????????????? ???? ???????? ??????????????
    private void checkPickedViews(){
        //???????? ?????? ?????? ???? ???????????? ?????????? true, ???????? ?????? ???? ???????? ?????????????? ?? ???? ?????????????? ???? false
        if (relativeListsViewModel.checkPickedCells()) {
            //Toast.makeText(getContext(), "Equals", Toast.LENGTH_SHORT).show();
            relativeListsViewModel.getSession().correct++;
            Thread thread = new Thread(fadeOutAnimation);
            thread.start();
        }
        //???????? ?????????? ?????? ????????????????, ???? ???????????? ?????????? ??????????
        else if(pickedViewByKey != null && pickedViewByValue!=null) {
            relativeListsViewModel.getSession().incorrect++;
            Toast.makeText(getContext(),
                    getResources().getString(R.string.toast_wrong_picked_cells),
                    Toast.LENGTH_SHORT).show();
            Thread thread = new Thread(wrongCellsAnimation);
            thread.start();
        }
    }
    //?????????????? ?????????????????? Views ???? ?????????????? + ?????????????? cells ?? ViewModel
    private void removePickedElements(){
        pickedViewByValue = null;
        pickedViewByKey = null;
        relativeListsViewModel.removePickedCells();
        if (relativeListsViewModel.getAdapterByKey().cellList.size() == 0){
            progressBar.setProgress(100);
        }
    }
    private void progressBarProcessing(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                relativeListsViewModel.setSessionRunning(true);
                while (relativeListsViewModel.SessionRunning){
                    if (progressBar.getProgress() >= 100){
                        progressBar.post(()->{
                            progressBar.setProgress(0);
                        });
                        relativeListsViewModel.setSessionRunning(false);
                    }
                    else {
                        progressBar.post(()->{
                            progressBar.setProgress(progressBar.getProgress() + 1);
                        });
                    }
                    android.os.SystemClock.sleep(relativeListsViewModel.getProgressBarDelay());
                }
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.main_session_fragment,
                                SessionResultFragment.newInstance(relativeListsViewModel.getSession()),"session_result")
                        .addToBackStack(null)
                        .commit();

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void sessionBtnInit(){
        endSessionBtn.setOnClickListener((View v)->{
            progressBar.setProgress(100);
        });
        promptBtn.setOnClickListener((View v)->{
            Cell currentPickedCell = null;
            if (relativeListsViewModel.getPickedCellFromListByKey() != null)
                currentPickedCell = relativeListsViewModel.getPickedCellFromListByKey();
            else if (relativeListsViewModel.getPickedCellFromListByValue() != null)
                currentPickedCell = relativeListsViewModel.getPickedCellFromListByValue();

            if (currentPickedCell != null){
                relativeListsViewModel.getSession().prompt++;
                for(int i = 0; i < relativeListsViewModel.getAdapterByKey().getCount();i++){
                    if (currentPickedCell.equals(relativeListsViewModel.getAdapterByKey().getItem(i))){
                        View tempView = relativeListsViewModel.getAdapterByKey().getViewByPosition(i,keyListView);
                        tempView.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_info));
                    }
                    if (currentPickedCell.equals(relativeListsViewModel.getAdapterByValue().getItem(i))){
                        View tempView = relativeListsViewModel.getAdapterByValue().getViewByPosition(i,valueListView);
                        tempView.setBackgroundColor(getResources().getColor(R.color.bootstrap_brand_info));
                    }
                }
            }
            else Toast.makeText(getContext(),getResources().getString(R.string.toast_no_picked_elements),Toast.LENGTH_SHORT).show();
        });
    }
}
