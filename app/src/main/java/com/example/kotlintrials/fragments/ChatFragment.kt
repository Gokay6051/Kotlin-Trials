package com.example.kotlintrials.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.kotlintrials.R
import com.example.kotlintrials.Utils
import com.example.kotlintrials.databinding.FragmentChatBinding
import com.example.kotlintrials.mvvm.ChatAppViewModel
import de.hdodenhof.circleimageview.CircleImageView

class ChatFragment : Fragment() {
    private lateinit var args: ChatFragmentArgs
    private lateinit var chatbinding: FragmentChatBinding
    private lateinit var chatAppViewModel: ChatAppViewModel
    private lateinit var chatToolbar: Toolbar
    private lateinit var circleImageView: CircleImageView
    private lateinit var tvUserName: TextView
    private lateinit var tvStatus: TextView
    private lateinit var backBtn: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        chatbinding = FragmentChatBinding.inflate(inflater, container, false)
        return chatbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args = ChatFragmentArgs.fromBundle(requireArguments())
        chatAppViewModel = ViewModelProvider(this).get(ChatAppViewModel::class.java)

        chatToolbar = view.findViewById(R.id.toolBarChat)
        circleImageView = chatToolbar.findViewById(R.id.chatImageViewUser)
        tvStatus = view.findViewById(R.id.chatUserStatus) //neden chatToolBar yerine view kullanıldı?
        tvUserName = view.findViewById(R.id.chatUserName)
        backBtn = chatToolbar.findViewById(R.id.chatBackBtn)

        backBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_chatFragment_to_homeFragment)
        }

        Glide.with(requireContext())
            .load(args.users.profileImageUrl)
            .into(circleImageView)
        tvStatus.setText(args.users.status)
        tvUserName.setText(args.users.userName)

        chatbinding.viewModel = chatAppViewModel
        chatbinding.lifecycleOwner = viewLifecycleOwner


        chatbinding.sendBtn.setOnClickListener {
            chatAppViewModel.sendMessage(Utils.getUidLoggedIn(), args.users.uid!!, args.users.userName!!, args.users.profileImageUrl!!, )
        }
    }
}