package com.example.memfixref.ui.dialog;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.example.memfixref.MainActivity.REQUEST_AUDIO_PERMISSION_CODE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.memfixref.MainActivity;
import com.example.memfixref.R;
import com.example.memfixref.ui.mainfragments.kit.onekitdata.KitViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import database.entities.Cell;
import services.BitmapScaler;

public class CellDialogFragment extends DialogFragment {
    //UI
    private EditText cellKeyEditText;
    private EditText cellValueEditText;
    private BootstrapButton confirmBtn;
    private BootstrapButton removeCellBtn;
    private ImageButton addImageBtn;
    private ImageButton addRecordBtn;

    //Record audio
    public Boolean isMicOn = false;
    private MediaRecorder mRecorder;
    //Image
    private ActivityResultLauncher<Intent> loadImageResultLauncher;

    private Cell cell;

    public static CellDialogFragment newInstance(int cellIndex) {

        Bundle args = new Bundle();
        args.putInt("index",cellIndex);
        CellDialogFragment fragment = new CellDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Обработка выбора изображения
        loadImageResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        try {
                            Uri imageUri = result.getData().getData();
                            Bitmap image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                            Bitmap scaleImage = BitmapScaler.scaleToFitWidth(image,250);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            scaleImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                            byte[] imageByteArray = stream.toByteArray();
                            cell.image = imageByteArray;
                            Toast.makeText(context, getResources().getString(R.string.toast_success_image_import), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(context, getResources().getString(R.string.toast_cant_find_image), Toast.LENGTH_SHORT).show();
                        }
                        // конвертнуть изображение и сохранить его + путь в базу + отобразить в списке
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState){
        return inflater.inflate(R.layout.add_cell_dialog,group,false);
    }
    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState){
        KitViewModel kitViewModel = new ViewModelProvider(getActivity()).get(KitViewModel.class);

        cellKeyEditText = view.findViewById(R.id.cellKey);
        cellValueEditText = view.findViewById(R.id.cellValue);
        confirmBtn = view.findViewById(R.id.confirmBtn);
        removeCellBtn = view.findViewById(R.id.removeCellBtn);

        addImageBtn = view.findViewById(R.id.addImageBtn);
        addRecordBtn = view.findViewById(R.id.addRecordBtn);

        mRecorder = new MediaRecorder();

        //Если редактируется уже существующая
        if (getArguments() != null && getArguments().containsKey("index")){
            int index = getArguments().getInt("index");
            cell = kitViewModel.getKit().cells.get(index);
            cellKeyEditText.setText(cell.getKey());
            cellValueEditText.setText(cell.getValue());
        }
        else {
            //Если создается новая запись
            cell = new Cell("key","value");
            kitViewModel.getKit().cells.add(cell);
        }
        addRecordBtn.setOnClickListener((View v)->{

            if (checkPermissions()) {
                if (isMicOn) {
                    addRecordBtn.setImageResource(R.drawable.ic_mic_off);
                    mRecorder.stop();
                    mRecorder.release();
                } else {
                    addRecordBtn.setImageResource(R.drawable.ic_mic_on);
                    if (cell.recordPath == null || cell.recordPath.equals("")) {
                        //Если запись происходит впервые, то создать имя файла(имя = дата первого создания)
                        cell.recordPath = Environment.getExternalStorageDirectory().getPath();
                        cell.recordPath += "/" + new Timestamp(System.currentTimeMillis()).getTime() + ".3gp";
                    }

                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    mRecorder.setOutputFile(cell.recordPath);

                    try {
                        mRecorder.prepare();

                        mRecorder.start();
                    } catch (IOException e) {
                        Log.d("Error", e.getMessage());
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                isMicOn = !isMicOn;
            }
            else {
                requestPermissions();
            }



        });

        addImageBtn.setOnClickListener((View v)->{
            Toast.makeText(getContext(),"Show Gallery",Toast.LENGTH_SHORT).show();
            //Вызвать галеррею
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            loadImageResultLauncher.launch(intent);
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cell.key = cellKeyEditText.getText().toString();
                cell.value = cellValueEditText.getText().toString();

                kitViewModel.getArrayAdapter().notifyDataSetChanged();
                dismiss();
            }
        });
        removeCellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    kitViewModel.removeCell(cell);
                }
                catch (Exception e){
                    Toast.makeText(getContext(),
                            getContext().getResources().getString(R.string.toast_reload_app),
                            Toast.LENGTH_LONG).show();
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    public boolean checkPermissions() {
        // this method is used to check permission
        int result = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        // this method is used to request the
        // permission for audio recording and storage.
        ActivityCompat.requestPermissions(getActivity(), new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }
}
