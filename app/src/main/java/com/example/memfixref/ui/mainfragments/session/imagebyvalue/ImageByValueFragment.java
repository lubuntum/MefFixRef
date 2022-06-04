package com.example.memfixref.ui.mainfragments.session.imagebyvalue;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.session.endresult.SessionResultFragment;

import database.entities.Cell;
import database.entities.Kit;

public class ImageByValueFragment extends Fragment {
    private ImageByValueViewModel viewModel;

    private ImageView cellImage;
    private TextView cellTextView;
    private BootstrapButton correctBtn;
    private BootstrapButton wrongBtn;

    public static ImageByValueFragment newInstance(Kit kit) {

        Bundle args = new Bundle();
        args.putSerializable("kit",kit);
        ImageByValueFragment fragment = new ImageByValueFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null && getArguments().containsKey("kit")){
            Kit kit = (Kit)getArguments().getSerializable("kit");
            viewModel = new ViewModelProvider(this,new ImageByValueViewModelFactory(
                    getActivity().getApplication(),kit)).get(ImageByValueViewModel.class);
            viewModel.resetSession();
        }
        return inflater.inflate(R.layout.fragment_session_image_by_value,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cellImage = view.findViewById(R.id.cellImage);
        cellTextView = view.findViewById(R.id.cellTextView);
        correctBtn = view.findViewById(R.id.correctBtn);
        wrongBtn = view.findViewById(R.id.wrongBtn);
        setDataForView();
        correctBtn.setOnClickListener((View v)->{
            if (viewModel.getCurrentCell().value.equals(cellTextView.getText().toString())){
                Toast.makeText(getContext()
                        ,getResources().getString(R.string.toast_correct)
                        , Toast.LENGTH_SHORT).show();
                viewModel.getSession().correct++;
                setDataForView();
            }
            else{
                Toast.makeText(getContext()
                        , getResources().getString(R.string.toast_wrong)
                        , Toast.LENGTH_SHORT).show();
                viewModel.getSession().incorrect++;
                cellTextView.setText(viewModel.nextValue());
            }
        });
        wrongBtn.setOnClickListener((View v)->{
            if (viewModel.getCurrentCell().value.equals(cellTextView.getText().toString())){
                Toast.makeText(getContext()
                        , getResources().getString(R.string.toast_wrong)
                        , Toast.LENGTH_SHORT).show();
                viewModel.getSession().incorrect++;
                setDataForView();
            }
            else {
                Toast.makeText(getContext()
                        ,getResources().getString(R.string.toast_correct)
                        , Toast.LENGTH_SHORT).show();
                viewModel.getSession().correct++;
                cellTextView.setText(viewModel.nextValue());
            }
        });
    }
    private void setDataForView(){
        if (viewModel.getCurrentCellIndex() >= viewModel.getKit().cells.size()){
            getParentFragmentManager().beginTransaction().
                    setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right).
                    replace(R.id.main_session_fragment,
                            SessionResultFragment.newInstance(viewModel.getSession()),
                            "session_result").
                    addToBackStack(null).commit();

        }
        else {
            Cell currentCell = viewModel.nextCell();
            viewModel.generateVariants(currentCell);
            cellImage.setImageBitmap(BitmapFactory.decodeByteArray(currentCell.image, 0, currentCell.image.length));
            cellTextView.setText(viewModel.nextValue());// должно быть рандомное значение
        }
    }

    /*
    private void progressBarProcessing(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
    }
     */
}
