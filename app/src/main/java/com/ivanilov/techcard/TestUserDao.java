package com.ivanilov.techcard;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

    @Dao
    public interface TestUserDao {

        // Добавление Person в бд
        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insertAll(TestRoomUser... testRoomUsers);

//        @Query("DELETE FROM users")
//        void delete (TestRoomUser testRoomUsers);

        // Получение всех Person из бд
        @Query("SELECT * FROM users")
        List<TestRoomUser> getAllPeople();

//        // Получение всех Person из бд с условием
//        @Query("SELECT * FROM person WHERE favoriteColor LIKE :color")
//        List<TestRoomUser> getAllPeopleWithFavoriteColor(String color);

    }


