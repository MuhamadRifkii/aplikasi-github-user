package com.dicoding.proyeksubmissionfundamental.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from Favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>


    @Query("SELECT EXISTS(SELECT 1 FROM Favorite WHERE username = :username LIMIT 1)")
    fun checkIsUserFavorite(username: String): LiveData<Boolean>

}