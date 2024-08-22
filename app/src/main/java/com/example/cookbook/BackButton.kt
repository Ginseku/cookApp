package com.example.cookbook

import android.view.View
import android.widget.Button
import androidx.navigation.NavController

object BackButton {
    fun setupButtonNavigation(view: View?, navController: NavController, buttonId: Int, destinationFragmentId: Int) {
        val button = view?.findViewById<Button>(buttonId)
        button?.setOnClickListener {
            navController.navigate(destinationFragmentId)
        }
    }
}