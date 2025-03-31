package com.example.text.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.text.Fragment.MainFragment
import com.example.text.R
import com.example.text.ViewModel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navigationButton: BottomNavigationView
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val mainFragment = MainFragment()
        replaceFragment(mainFragment)

        navigationButton = findViewById(R.id.bottom_navigation)
        setupNavigationListener()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    private fun setupNavigationListener() {
        navigationButton.setOnNavigationItemSelectedListener { item ->
            mainViewModel.handleNavigationItemSelected(this, item.itemId)
        }
    }
}