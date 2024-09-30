package com.example.kotlintrials.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintrials.R
import com.example.kotlintrials.activities.MainActivity.Companion.firebaseManagement
import com.example.kotlintrials.adapters.UserAdapter
import com.example.kotlintrials.databinding.ActivityRegisterBinding
import com.example.kotlintrials.databinding.FragmentHomeBinding
import com.example.kotlintrials.mvvm.ChatAppViewModel
import kotlin.math.log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlintrials.activities.LoginActivity

class HomeFragment : Fragment() {
    lateinit var rvUsers : RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var userViewModel: ChatAppViewModel
    lateinit var homeBinding: FragmentHomeBinding
    private lateinit var alrtDialog: android.app.AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ChatAppViewModel()
        userAdapter = UserAdapter()
        rvUsers = homeBinding.rvUsers
        rvUsers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false) //added
        rvUsers.adapter = userAdapter
        userViewModel.getUsers().observe(viewLifecycleOwner, {
            Log.d("HomeFragment", "Gelen kullanıcılar: $it")
            userAdapter.setUserList(it)
        })

        homeBinding.logOut.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        alrtDialog = android.app.AlertDialog.Builder(requireContext()).create()
        alrtDialog.setTitle("Are you sure you want to log out?")
        alrtDialog.setCancelable(false)

        alrtDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Yes") { _, _ ->
            firebaseManagement.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()
        }
        alrtDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "No") { _, _ ->
            alrtDialog.dismiss()
        }

        alrtDialog.show()
    }
}