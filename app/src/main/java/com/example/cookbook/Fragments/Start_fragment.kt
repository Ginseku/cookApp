package com.example.cookbook.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookbook.R
import com.example.cookbook.databinding.StartFragmentBinding

//Начальная страница
class Start_fragment : Fragment() {
    private lateinit var binding: StartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startScreenButton.setOnClickListener {
            // При нажатии кнопки переключаемся на новый фрагмент
            childFragmentManager.beginTransaction()
                .replace(R.id.constraint_l_first_frag, SecondFragment())
                .addToBackStack(null)
                .commit()
        }

    }
}