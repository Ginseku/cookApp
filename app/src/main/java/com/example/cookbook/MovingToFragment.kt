package com.example.cookbook

import android.os.Bundle
import androidx.fragment.app.Fragment

object MovingToFragment {
    fun Fragment.sendIdToAnotherFragment(id: String) {
        val result = Bundle().apply {
            putString("id_key", id)
        }
        parentFragmentManager.setFragmentResult("request_key", result)
    }
}