package com.ivanilov.techcard.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.*;
import androidx.room.Room;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.ivanilov.techcard.Model.HistoryTable;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.R;
import com.ivanilov.techcard.View.Fragments.HistoryFragment;
import com.ivanilov.techcard.View.Fragments.IngredientsFragment;
import com.ivanilov.techcard.View.Fragments.ProductFragment;
import com.ivanilov.techcard.View.Fragments.TechCartFragment;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, IngredientsFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, TechCartFragment.OnFragmentInteractionListener {

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager = getSupportFragmentManager();
    Toolbar toolbar;
    IngerdientTable db;
    TechCartTable techCartTable;
    HistoryTable historyTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation_view);

        drawerLayout = findViewById(R.id.drawer);


        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();


        IngredientsFragment ingredientsFragment = new IngredientsFragment();

        fragmentTransaction.add(R.id.container, ingredientsFragment);
        fragmentTransaction.commit();

        db = Room.databaseBuilder(getApplicationContext(),
                IngerdientTable.class, "ingerdient_database").allowMainThreadQueries().build();

        techCartTable = Room.databaseBuilder(getApplicationContext(),
                TechCartTable.class, "techcart_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();

        historyTable = Room.databaseBuilder(getApplicationContext(),
                HistoryTable.class, "history_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();


        if (menuItem.getTitle().equals("Продукты")) {

            IngredientsFragment ingredientsFragment = new IngredientsFragment();

            fragmentTransaction.replace(R.id.container, ingredientsFragment);
            fragmentTransaction.commit();
            toolbar.setTitle("Продукты");

        }

        if (menuItem.getTitle().equals("Товары")) {

            ProductFragment productFragment = new ProductFragment();

            fragmentTransaction.replace(R.id.container, productFragment);
            fragmentTransaction.commit();
            toolbar.setTitle("Товары");

        }

        if (menuItem.getTitle().equals("Технологические карты")) {

            TechCartFragment techCartFragment = new TechCartFragment();

            fragmentTransaction.replace(R.id.container, techCartFragment);
            fragmentTransaction.commit();
            toolbar.setTitle("Технологические карты");

        }

        if (menuItem.getTitle().equals("История")) {

            HistoryFragment historyFragment = new HistoryFragment();

            fragmentTransaction.replace(R.id.container, historyFragment);
            fragmentTransaction.commit();
            toolbar.setTitle("История");

        }

        drawerLayout.closeDrawer(Gravity.LEFT);
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public IngerdientTable getDbIngerdient() {
        return db;
    }

    public TechCartTable getTechCartTable() {
        return techCartTable;
    }

    public HistoryTable getHistoryTable() {
        return historyTable;
    }

}
