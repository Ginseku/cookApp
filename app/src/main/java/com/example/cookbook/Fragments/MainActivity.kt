package com.example.cookbook.Fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cookbook.Listener
import com.example.cookbook.R

class MainActivity : AppCompatActivity(), Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(itemId: Int) {
        Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show()
    }
}
