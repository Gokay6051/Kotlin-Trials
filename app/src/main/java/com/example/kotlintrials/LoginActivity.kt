package com.example.kotlintrials

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.kotlintrials.databinding.ActivityLoginBinding

class LoginActivity : ComponentActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //Burayı kaldırınca da çalışabilir
        /*
        if (MainActivity.isLoggedIn) {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish the current activity to prevent going back to it
        }
        */

        binding.buttonLogin.setOnClickListener() {
            val userName = binding.editTextUserName.text.toString()
            val password = binding.editTextPassword.text.toString()

            if(userName.isNotEmpty() && password.isNotEmpty()){
                MainActivity.auth.signInWithEmailAndPassword(userName, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.editTextUserName.text.clear()
                        binding.editTextPassword.text.clear()
                        Toast.makeText(applicationContext, "User sign in successfully", Toast.LENGTH_LONG).show()
                        intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener() {
                    Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonRegister.setOnClickListener() {
            intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}