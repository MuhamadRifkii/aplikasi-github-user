package com.dicoding.proyeksubmissionfundamental.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.proyeksubmissionfundamental.databinding.ActivityFavoriteListBinding
import com.dicoding.proyeksubmissionfundamental.helper.FavoriteViewModelFactory
import com.dicoding.proyeksubmissionfundamental.ui.adapter.FavoriteAdapter
import com.dicoding.proyeksubmissionfundamental.ui.viewmodel.FavoriteViewModel

class FavoriteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteListBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.listFavorite.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter()
        binding.listFavorite.adapter = favoriteAdapter

        val factory = FavoriteViewModelFactory.getInstance(application)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        favoriteViewModel.getAllFavorite().observe(this) { favoriteUsers ->
            favoriteAdapter.submitList(favoriteUsers)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}