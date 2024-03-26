package com.dicoding.proyeksubmissionfundamental.data.repository

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.proyeksubmissionfundamental.database.Favorite

class FavoriteAddUpdateViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }

    fun checkIsUserFavorite(username: String): LiveData<Boolean> {
        return mFavoriteRepository.checkIsUserFavorite(username)
    }
}