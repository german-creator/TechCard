package com.ivanilov.techcard.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ivanilov.techcard.Presenter.Entity.Ingredient;

import java.util.List;

@Dao
public interface IngerdientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Ingredient... ingredients);

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAllIngredient();

    @Delete
    void delete(Ingredient ingredient);
}
