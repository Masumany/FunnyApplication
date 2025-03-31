package com.example.text.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.text.Fragment.CheckFragment
import com.example.text.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class CheckActivity : AppCompatActivity() {

    private lateinit var navigationButton: BottomNavigationView
    private lateinit var checkViewModel: CheckViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        checkViewModel = ViewModelProvider(this).get(CheckViewModel::class.java)

        val checkFragment = CheckFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, checkFragment)
        fragmentTransaction.commit()

        navigationButton = findViewById(R.id.bottom_navigation)

        navigationButton.setOnNavigationItemSelectedListener { item ->
            checkViewModel.onNavigationItemSelected(item.itemId)
            true
        }

        checkViewModel.navigationEvent.observe(this, Observer { targetClass ->
            if (targetClass != null) {
                val intent = Intent(this, targetClass)
                startActivity(intent)
            }
        })

        navigationButton.selectedItemId = R.id.menu_item_check
    }
}