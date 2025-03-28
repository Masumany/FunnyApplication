package com.example.text

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class CheckActivity : AppCompatActivity() {

    private lateinit var navigationButton: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        val checkFragment= CheckFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, checkFragment)
        fragmentTransaction.commit()

        navigationButton = findViewById(R.id.bottom_navigation)

        navigationButton.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_main -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_item_check -> {

                    true
                }
                R.id.menu_item_mine -> {
                    val intent = Intent(this, MineActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
           navigationButton.selectedItemId =   R.id.menu_item_check
        }
    }
