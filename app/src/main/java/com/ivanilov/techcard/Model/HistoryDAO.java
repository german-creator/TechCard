package com.ivanilov.techcard.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ivanilov.techcard.Presenter.Entity.History;
import com.ivanilov.techcard.Presenter.Entity.TechCart;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History... history);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<History> histories);

    @Query("SELECT * FROM History ORDER BY date DESC")
    List<History> getAllHistory();

    @Delete
    void delete(History history);

    @Query("DELETE  FROM History")
    void deleteAll();
}
