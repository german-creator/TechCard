package com.ivanilov.techcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.TextView;

import com.ivanilov.techcard.Model.Ingredient;
import com.ivanilov.techcard.Presenter.IngerdientTable;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IngerdientTable db = Room.databaseBuilder(getApplicationContext(),
                IngerdientTable.class, "database-ingredient").allowMainThreadQueries().build();


        db.ingerdientDAO().insertAll(new Ingredient("Молоко", "Литры", 20.00));
        List<Ingredient> everyone = db.ingerdientDAO().getAllIngredient();


//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "database-name").allowMainThreadQueries().build();
//
//
//        db.testUserDao().insertAll(new TestRoomUser("2343", "Soffia"));
//        List<TestRoomUser> everyone = db.testUserDao().getAllPeople();

        TextView textView = findViewById(R.id.testTextView);


        textView.setText(everyone.get(0).getName());

    }
}
