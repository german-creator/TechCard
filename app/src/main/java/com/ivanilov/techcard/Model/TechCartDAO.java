package com.ivanilov.techcard.Model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ivanilov.techcard.Presenter.Entity.TechCart;

import java.util.List;

@Dao
public interface TechCartDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TechCart... techCarts);

    @Query("SELECT * FROM TechCart")
    List<TechCart> getAllTechCart();

    @Delete
    void delete(TechCart techCart);

    @Query("DELETE FROM TechCart")
    void deleteAll();
}
