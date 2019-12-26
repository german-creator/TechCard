package com.ivanilov.techcard.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.*;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ivanilov.techcard.R;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, IngredientsFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, TechCartFragment.OnFragmentInteractionListener {

    NavigationView navigationView;
    DrawerLayout drawerLayoutl;
    FragmentManager fragmentManager = getSupportFragmentManager();
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation_view);

        drawerLayoutl = findViewById(R.id.drawer);


        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();


        IngredientsFragment ingredientsFragment = new IngredientsFragment();

//            if (fragmentTransaction.isEmpty()) {
        fragmentTransaction.add(R.id.container, ingredientsFragment);
        fragmentTransaction.commit();


//        IngerdientTable db = Room.databaseBuilder(getApplicationContext(),
//                IngerdientTable.class, "database-ingredient").allowMainThreadQueries().build();
//
//
//        db.ingerdientDAO().insertAll(new Ingredient("Молоко", "Литры", 20.00));
//        List<Ingredient> everyone = db.ingerdientDAO().getAllIngredient();


//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "database-name").allowMainThreadQueries().build();
//
//
//        db.testUserDao().insertAll(new TestRoomUser("2343", "Soffia"));
//        List<TestRoomUser> everyone = db.testUserDao().getAllPeople();

//        TextView textView = findViewById(R.id.testTextView);
//
//
//        textView.setText(everyone.get(0).getName());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayoutl.openDrawer(Gravity.LEFT);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();


        if (menuItem.getTitle().equals("Ингридиенты")) {

            IngredientsFragment ingredientsFragment = new IngredientsFragment();

            fragmentTransaction.replace(R.id.container, ingredientsFragment);
            fragmentTransaction.commit();
            toolbar.setTitle("Ингридиенты");

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

        drawerLayoutl.closeDrawer(Gravity.LEFT);
        return false;
}

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
