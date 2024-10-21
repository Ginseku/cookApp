package com.example.cookbook.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookbook.ApiService
import com.example.cookbook.LoginCredentials
import com.example.cookbook.R
import com.example.cookbook.UserData
import com.example.cookbook.databinding.FragmentAuthorizationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инициализация привязки
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root // Возврат корневого представления
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentUser.reload()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    // Нажатие на кнопку регистрации
        binding.buttonRegister.setOnClickListener {
            Log.d("AuthorizationFragment", "Register button clicked")
            showRegistrationFields()
            binding.imageViewCap.visibility = View.INVISIBLE
            binding.imageViewCircle.visibility = View.INVISIBLE
        }

        // Нажатие на кнопку логина
        binding.buttonLogin.setOnClickListener {
            Log.d("AuthorizationFragment", "Login button clicked")
            showLoginFields()
            binding.imageViewCap.visibility = View.INVISIBLE
            binding.imageViewCircle.visibility = View.INVISIBLE
        }
        // Нажатие на кнопку отправки данных
        binding.buttonSubmit.setOnClickListener {
            Log.d("AuthorizationFragment", "Submit button clicked")
            val emailOrNickname = binding.editTextEmailNickname.text.toString()
            val password = binding.editTextPassword.text.toString()
            val nickname = binding.editTextNickname.text.toString()
            if (binding.editTextNickname.visibility == View.VISIBLE) {
                Log.d("AuthorizationFragment", "Registering user")
                //registerUser(emailOrNickname, nickname, password)
            } else {
                Log.d("AuthorizationFragment", "Logging in user")
                //loginUser(emailOrNickname, password)
            }
        }
    }



    // Показать поля для регистрации
    private fun showRegistrationFields() {
        Log.d("AuthorizationFragment", "Showing registration fields")
        binding.editTextEmailNickname.visibility = View.VISIBLE
        binding.editTextNickname.visibility = View.VISIBLE
        binding.editTextPassword.visibility = View.VISIBLE
        binding.buttonSubmit.visibility = View.VISIBLE
        binding.editTextNickname.hint = "Enter Nickname" // Уникальное для регистрации
        binding.buttonSubmit.text = "Register"
    }
    // Показать поля для логина
    private fun showLoginFields() {
        Log.d("AuthorizationFragment", "Showing login fields")
        binding.editTextEmailNickname.visibility = View.VISIBLE
        binding.editTextPassword.visibility = View.VISIBLE
        binding.buttonSubmit.visibility = View.VISIBLE
        binding.editTextNickname.visibility = View.GONE
        binding.editTextEmailNickname.hint = "Email or Nickname" // Уникальное для логина
        binding.buttonSubmit.text = "Login"
    }

}

