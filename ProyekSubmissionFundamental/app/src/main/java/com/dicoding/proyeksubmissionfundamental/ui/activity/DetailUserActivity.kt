package com.dicoding.proyeksubmissionfundamental.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.proyeksubmissionfundamental.R
import com.dicoding.proyeksubmissionfundamental.data.repository.FavoriteAddUpdateViewModel
import com.dicoding.proyeksubmissionfundamental.database.Favorite
import com.dicoding.proyeksubmissionfundamental.databinding.ActivityDetailUserBinding
import com.dicoding.proyeksubmissionfundamental.ui.adapter.SectionsPagerAdapter
import com.dicoding.proyeksubmissionfundamental.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import de.hdodenhof.circleimageview.CircleImageView

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteAddUpdateViewModel: FavoriteAddUpdateViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)

        setContentView(binding.root)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        favoriteAddUpdateViewModel = ViewModelProvider(this).get(FavoriteAddUpdateViewModel::class.java)

        val username = intent.getStringExtra("username")
        if (username != null) {
            detailViewModel.setUser(username)
            checkIsUserFavorite(username)
        }

        val photo: CircleImageView = findViewById(R.id.photo)
        val name: TextView = findViewById(R.id.name)
        val login: TextView = findViewById(R.id.login)
        val followers: TextView = findViewById(R.id.followers)
        val following: TextView = findViewById(R.id.following)
        val progressBar: ProgressBar = findViewById(R.id.progressBar2)

        detailViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        detailViewModel.user.observe(this) { user ->
            Glide.with(this)
                .load(user.avatarUrl)
                .into(photo)
            name.text = user.name
            login.text = user.login
            followers.text = getString(R.string.followers_count, user.followers)
            following.text = getString(R.string.following_count, user.following)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username.toString())
        val viewPager : ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        binding.fab.setOnClickListener {
            saveToFavorite()
        }
    }

    private fun checkIsUserFavorite(username: String) {
        favoriteAddUpdateViewModel.checkIsUserFavorite(username).observe(this) { isFavorite ->
            if (isFavorite) {
                binding.fab.setImageResource(R.drawable.favorite_fill)
                binding.fab.setOnClickListener {
                    deleteFromFavorite()
                }
            } else {
                binding.fab.setImageResource(R.drawable.favorite_border)
                binding.fab.setOnClickListener {
                    saveToFavorite()
                }
            }
        }
    }

    private fun saveToFavorite() {
        val username = intent.getStringExtra("username")
        val avatarUrl = intent.getStringExtra("avatarUrl")

        if (username != null && avatarUrl != null) {
            val favorite = Favorite(username = username, avatarUrl = avatarUrl)
            favoriteAddUpdateViewModel.insert(favorite)

            Toast.makeText(this, "Berhasil menambahkan user ke favorite", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Gagal menambahkan user ke favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteFromFavorite() {
        val username = intent.getStringExtra("username")
        val avatarUrl = intent.getStringExtra("avatarUrl")

        if (username != null && avatarUrl != null) {
            val favoriteUser = Favorite(username = username, avatarUrl = avatarUrl)
            favoriteAddUpdateViewModel.delete(favoriteUser)

            Toast.makeText(this, "Berhasil menghapus user dari favorite", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Gagal menghapus user ke favorite", Toast.LENGTH_SHORT).show()
        }
    }

}