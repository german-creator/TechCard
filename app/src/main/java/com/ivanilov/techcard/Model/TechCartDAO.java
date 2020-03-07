package com.ivanilov.techcard.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ivanilov.techcard.Presenter.Entity.Ingredient;
import com.ivanilov.techcard.Presenter.Entity.TechCart;

import java.util.List;

@Dao
public interface TechCartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TechCart... techCarts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList (List<TechCart> techCarts);

    @Query("SELECT * FROM TechCart ORDER BY name")
    List<TechCart> getAllTechCart();

    @Query("SELECT * FROM TechCart WHERE name = :name ")
    TechCart getTechCartByName(String name);

    @Delete
    void delete(TechCart techCart);

    @Query("DELETE  FROM TechCart")
    void deleteAll();
}
