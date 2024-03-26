package com.dicoding.proyeksubmissionfundamental.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.proyeksubmissionfundamental.data.repository.FavoriteRepository
import com.dicoding.proyeksubmissionfundamental.database.Favorite

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository : FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorite(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorite()
}