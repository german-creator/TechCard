package com.ivanilov.techcard.Presenter;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ivanilov.techcard.Model.Ingredient;

import java.util.List;

@Dao
public interface IngerdientDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Ingredient... ingredients);

    @Query("SELECT * FROM ingredient")
    List<Ingredient> getAllIngredient();
}
