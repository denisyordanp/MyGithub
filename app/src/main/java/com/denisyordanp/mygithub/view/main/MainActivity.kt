package com.denisyordanp.mygithub.view.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.denisyordanp.mygithub.R
import com.denisyordanp.mygithub.databinding.ActivityMainBinding
import com.denisyordanp.mygithub.utils.MainViewModelFactory
import com.denisyordanp.mygithub.utils.SettingPreferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var mainViewModel: MainViewModel? = null
    private var isDarkModeActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyGithub_Main)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupActionBar()
        setupListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        isDarkModeActive =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.theme) {
            applyTheme()
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun applyTheme() {
        mainViewModel?.saveThemeSetting(!isDarkModeActive)
    }

    private fun setupViewModel() {
        val pref = SettingPreferences.getInstance(dataStore)
        val factory = MainViewModelFactory.getInstance(pref)
        mainViewModel =
            ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun setupActionBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupListener() {
        mainViewModel?.isDarkModeActive?.observe(this) {
            setTheme(it)
        }
    }

    private fun setTheme(isDarkModeActive: Boolean) {
        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}