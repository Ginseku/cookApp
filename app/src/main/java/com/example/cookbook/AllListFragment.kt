package com.example.cookbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cookbook.databinding.AllListFragmentBinding
import com.example.cookbook.databinding.StartFragmentBinding

class AllListFragment: Fragment() {
    private lateinit var binding: AllListFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AllListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backBut()
    }

    private fun backBut() {
        val controler = findNavController()
        val b1 = view?.findViewById<Button>(R.id.but_back_ALF)
        b1?.setOnClickListener{
            controler.navigate(R.id.second_fragment)
        }
    }
}