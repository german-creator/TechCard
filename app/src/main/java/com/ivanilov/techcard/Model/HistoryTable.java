package com.ivanilov.techcard.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ivanilov.techcard.Presenter.Entity.History;
import com.ivanilov.techcard.Presenter.Entity.Ingredient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {History.class}, version = 1, exportSchema = false)
public abstract class HistoryTable extends RoomDatabase {

    public abstract HistoryDAO historyDAO();

    private static volatile HistoryTable INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static HistoryTable getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HistoryTable.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HistoryTable.class, "history_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
