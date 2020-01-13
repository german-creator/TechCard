package com.ivanilov.techcard.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ivanilov.techcard.Presenter.Entity.TechCart;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TechCart.class}, version = 3, exportSchema = false)
public abstract class TechCartTable extends RoomDatabase {

    public abstract TechCartDAO techCartDAO();

    private static volatile TechCartTable INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TechCartTable getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TechCartTable.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TechCartTable.class, "techcart_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
