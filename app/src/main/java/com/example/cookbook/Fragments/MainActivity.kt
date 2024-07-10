package com.example.cookbook.Fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookbook.Listener
import com.example.cookbook.R

class MainActivity : AppCompatActivity(), Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.main_activity, Start_fragment())
//                .commit()
//        }
    }

    override fun onClick(itemId: Int) {
        TODO("Not yet implemented")
    }
}
