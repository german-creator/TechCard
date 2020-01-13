package com.ivanilov.techcard.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ivanilov.techcard.Presenter.Entity.Ingredient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ingredient.class}, version = 1, exportSchema = false)
public abstract class IngerdientTable extends RoomDatabase {

    public abstract IngerdientDAO ingerdientDAO();

    private static volatile IngerdientTable INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static IngerdientTable getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (IngerdientTable.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IngerdientTable.class, "ingerdient_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
