package com.example.kotlintrials.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kotlintrials.FirebaseManagement
import com.example.kotlintrials.R
import com.example.kotlintrials.Utils
import com.example.kotlintrials.dataClasses.User
import com.example.kotlintrials.databinding.ActivityMainBinding
import com.example.kotlintrials.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    companion object {
        val firebaseManagement = FirebaseManagement()
        lateinit var db: FirebaseFirestore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFrag.navController


        db = FirebaseFirestore.getInstance()

        if (!firebaseManagement.isLoggedIn()) {
            Toast.makeText(applicationContext, "${firebaseManagement.isLoggedIn()}", Toast.LENGTH_SHORT).show()
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            firestore.collection(("Users")).whereEqualTo("uid", Utils.getUidLoggedIn()).get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0] // İlk belgenin alınması
                        document.reference.update("status", "online")
                            .addOnSuccessListener { Log.d("MainActivity", "User status updated to online") }
                            .addOnFailureListener { Log.d("MainActivity", "Error: ${it.localizedMessage}") }
                    } else {
                        Log.d("MainActivity", "Query snapshot is empty")
                    }
                }
                .addOnFailureListener { Log.d("MainActivity", "Error: ${it.localizedMessage}") }
        }
    }


    override fun onResume() {
        super.onResume()

        if(auth.currentUser != null) {
            if (auth.currentUser != null) {
                firestore.collection(("Users")).whereEqualTo("uid", Utils.getUidLoggedIn()).get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            val document = querySnapshot.documents[0] // İlk belgenin alınması
                            document.reference.update("status", "online")
                                .addOnSuccessListener { Log.d("MainActivity", "User status updated to online") }
                                .addOnFailureListener { Log.d("MainActivity", "Error: ${it.localizedMessage}") }
                        } else {
                            Log.d("MainActivity", "Query snapshot is empty")
                        }
                    }
                    .addOnFailureListener { Log.d("MainActivity", "Error: ${it.localizedMessage}") }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        if(auth.currentUser != null) {
            if (auth.currentUser != null) {
                firestore.collection(("Users")).whereEqualTo("uid", Utils.getUidLoggedIn()).get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            val document = querySnapshot.documents[0] // İlk belgenin alınması
                            document.reference.update("status", "offline")
                                .addOnSuccessListener { Log.d("MainActivity", "User status updated to online") }
                                .addOnFailureListener { Log.d("MainActivity", "Error: ${it.localizedMessage}") }
                        } else {
                            Log.d("MainActivity", "Query snapshot is empty")
                        }
                    }
                    .addOnFailureListener { Log.d("MainActivity", "Error: ${it.localizedMessage}") }
            }
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        }
        else {
            if(navController.currentDestination?.id == R.id.homeFragment) {
                moveTaskToBack(true)
            }
            else {
                super.onBackPressed()
            }
        }
    }

/*
    private fun displayCurrentUser(db: FirebaseFirestore) {
        val firebase = firebaseManagement.getCurrentUser()
        val uid = firebase?.uid ?: return

        Log.d("MainActivity", "UID: $uid")

        Companion.db.collection("users")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if(!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val user = document.toObject(User::class.java)
                    if(user != null) {
                        binding.textViewUserMain.text = "User Name: ${user.userName}"
                        binding.textViewPasswordMain.text = "Email: ${user.email}"
                    }
                    else {
                        Log.d("MainActivity", "User object is null")
                        Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Log.d("MainActivity", "Query snapshot is empty")
                    Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener() {
                Log.d("MainActivity", "Error: ${it.localizedMessage}")
                Toast.makeText(applicationContext, "User not found", Toast.LENGTH_SHORT).show()
            }

    }

 */
}