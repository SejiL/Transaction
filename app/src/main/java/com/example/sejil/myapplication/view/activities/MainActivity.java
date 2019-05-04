package com.example.sejil.myapplication.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.example.sejil.myapplication.R;
import com.example.sejil.myapplication.utility.CreateExpenseBottomSheet;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.bottom_app_bar_main)
    BottomAppBar bottomAppBar;
    @BindView(R.id.fab_create_expense)
    FloatingActionButton fab;

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBottomAppBar();

    }

    private void setUpBottomAppBar() {
        setSupportActionBar(bottomAppBar);
        fab.setOnClickListener(v -> {
            CreateExpenseBottomSheet bottomSheetDialogFragment = CreateExpenseBottomSheet.newInstance();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "fragment_create_expense");
        });
        bottomAppBar.setNavigationOnClickListener(v -> Toast.makeText(this, "Navigation", Toast.LENGTH_SHORT).show());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_statistics:
                Toast.makeText(this, "Statistics", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
