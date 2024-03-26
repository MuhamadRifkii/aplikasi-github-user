package com.dicoding.proyeksubmissionfundamental.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.proyeksubmissionfundamental.R
import com.dicoding.proyeksubmissionfundamental.data.response.ItemsItem
import com.dicoding.proyeksubmissionfundamental.databinding.ActivityMainBinding
import com.dicoding.proyeksubmissionfundamental.helper.SettingPreferences
import com.dicoding.proyeksubmissionfundamental.helper.SettingViewModelFactory
import com.dicoding.proyeksubmissionfundamental.helper.dataStore
import com.dicoding.proyeksubmissionfundamental.ui.adapter.UserAdapter
import com.dicoding.proyeksubmissionfundamental.ui.viewmodel.MainViewModel
import com.dicoding.proyeksubmissionfundamental.ui.viewmodel.SettingViewModel

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding
private val settingViewModel: SettingViewModel by viewModels {
        SettingViewModelFactory(SettingPreferences.getInstance(application.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        mainViewModel.username.observe(this) { username ->
            setUserName(username)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    val query = searchView.text.toString().trim()
                    if (query.isNotEmpty()) {
                        mainViewModel.listUser(query)
                    } else {
                        Toast.makeText(this@MainActivity, "Please enter a search query", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
            searchBar.apply {
                inflateMenu(R.menu.item_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_favorite -> {
                            startActivity(Intent(this@MainActivity, FavoriteListActivity::class.java))
                            true
                        }
                        R.id.menu_settings -> {
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    SettingActivity::class.java
                                )
                            )
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

    private fun setUserName(listUser : List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding.rvList.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}