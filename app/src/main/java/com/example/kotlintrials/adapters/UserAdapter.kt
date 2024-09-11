package com.example.kotlintrials.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlintrials.R
import com.example.kotlintrials.modal.Users
import com.google.firebase.firestore.util.Listener
import de.hdodenhof.circleimageview.CircleImageView


class UserAdapter : RecyclerView.Adapter<UserHolder>() {
    private var listOfUsers = listOf<Users>()
    private var listener: OnUserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.userlistitem, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val users = listOfUsers[position]
        val name = users.userName!!.split("\\s".toRegex())[0]
        holder.profileName.setText(name)

        if(users.status.equals("online")){
            holder.statusImageView.setImageResource(R.drawable.onlinestatus)
        }else{
            holder.statusImageView.setImageResource(R.drawable.offlinestatus)
        }

        Glide.with(holder.itemView.context)
            .load(users.profileImageUrl)
            .into(holder.imageProfile)

        holder.itemView.setOnClickListener {
            listener?.onUserSelected(position, users)
        }
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    fun setUserList(list: List<Users>) {
        this.listOfUsers = list
        notifyDataSetChanged()
    }

    fun setOnUserClickListener(listener: OnUserClickListener) {
        this.listener = listener
    }
}

class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //Burası düzenlenecek
    val profileName: TextView = itemView.findViewById(R.id.userName)
    val imageProfile : CircleImageView = itemView.findViewById(R.id.imageViewUser)
    val statusImageView: ImageView = itemView.findViewById(R.id.statusOnline)
}

interface OnUserClickListener {
    fun onUserSelected(position: Int, users: Users)
}