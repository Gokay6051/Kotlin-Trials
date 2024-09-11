package com.example.kotlintrials.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlintrials.R
import com.example.kotlintrials.adapters.UserAdapter
import com.example.kotlintrials.databinding.ActivityRegisterBinding
import com.example.kotlintrials.databinding.FragmentHomeBinding
import com.example.kotlintrials.mvvm.ChatAppViewModel

class HomeFragment : Fragment() {
    lateinit var rvUsers : RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var userViewModel: ChatAppViewModel
    lateinit var homeBinding: FragmentHomeBinding

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
        rvUsers.adapter = userAdapter
        userViewModel.getUsers().observe(viewLifecycleOwner, {
            userAdapter.setUserList(it)
        })
    }
}