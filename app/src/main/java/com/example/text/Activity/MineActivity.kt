package com.example.text.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.text.Fragment.MineFragment
import com.example.text.R
import com.example.text.ViewModel.MineViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MineActivity : AppCompatActivity() {
    private lateinit var navigationButton: BottomNavigationView
    private lateinit var mineViewModel: MineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)

        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)


        val mineFragment = MineFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, mineFragment)
        fragmentTransaction.commit()

        navigationButton = findViewById(R.id.bottom_navigation)
        initBottomNavigation()


    }

    private fun initBottomNavigation() {
        navigationButton.setOnNavigationItemSelectedListener { item ->
            mineViewModel.handleNavigationItemSelected(this,item.itemId)

        }
        navigationButton.selectedItemId = R.id.menu_item_mine

    }
}

