package com.example.kotlintrials.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.kotlintrials.activities.MainActivity.Companion.firebaseManagement
import com.example.kotlintrials.databinding.ActivityLoginBinding

class LoginActivity : ComponentActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var alrtDialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener() {
            signIn()
        }

        binding.checkBoxShowPassword.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.editTextPassword.transformationMethod = null
            } else {
                binding.editTextPassword.transformationMethod = android.text.method.PasswordTransformationMethod.getInstance()
            }
        }

        binding.buttonRegister.setOnClickListener() {
            intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signIn() {
        val userName = binding.editTextUserName.text.toString()
        val password = binding.editTextPassword.text.toString()
        if(userName.isNotEmpty() && password.isNotEmpty()){
            firebaseManagement.signIn(userName, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    alertMessage()

                    binding.editTextUserName.text.clear()
                    binding.editTextPassword.text.clear()
                    Toast.makeText(applicationContext, "User sign in successfully", Toast.LENGTH_LONG).show()
                    intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }.addOnFailureListener() {
                Toast.makeText(applicationContext, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(applicationContext, "Please fill all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun alertMessage() {
        alrtDialog = AlertDialog.Builder(this)
            .setTitle("Signing in")
            .setMessage("Please wait...")
            .setCancelable(false)
            .create()
        alrtDialog.show()
    }
}