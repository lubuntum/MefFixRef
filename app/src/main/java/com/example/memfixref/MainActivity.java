package com.example.memfixref;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.memfixref.ui.mainfragments.settings.SettingsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences settingsPreferences;

    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE},REQUEST_AUDIO_PERMISSION_CODE);
        }
        //установка "шапки"
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settingsPreferences = getSharedPreferences(
                SettingsViewModel.SETTINGS_STORAGE,
                Context.MODE_PRIVATE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);//основой контейнер
        NavigationView navigationView = findViewById(R.id.nav_view);//боковая панель
        View navHeader = navigationView.getHeaderView(0);

        TextView userName = navHeader.findViewById(R.id.userName);
        TextView userQuote = navHeader.findViewById(R.id.userQuote);

        userName.setText(settingsPreferences.getString(SettingsViewModel.USERNAME,"User"));
        userQuote.setText(settingsPreferences.getString(SettingsViewModel.QUOTE,"Quote"));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,  R.id.nav_settings, R.id.nav_my_data)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);//Nav потому что может сам взаимозаменять фрагменты
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);//добавляет кнопку для боковой панели с именем фрагмента
        NavigationUI.setupWithNavController(navigationView, navController);//связь элементов на боковой панели с заменяемыми фрагментами (одинаковый id item и нужного fragment)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}