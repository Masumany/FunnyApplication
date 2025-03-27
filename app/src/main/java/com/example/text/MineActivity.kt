package com.example.text

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MineActivity : AppCompatActivity() {
    private lateinit var navigationButton: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine)

        val mineFragment= MineFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, mineFragment)
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
                    val intent = Intent(this, CheckActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_item_mine -> {
                    true
                }
                else -> false
            }
        }
                navigationButton.selectedItemId =   R.id.menu_item_mine

        }

    }

