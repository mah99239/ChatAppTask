package com.mz.chatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mz.chatapp.databinding.ItemMessageReceivedBinding
import com.mz.chatapp.databinding.ItemUserBinding
import com.mz.chatapp.domain.model.Message
import com.mz.chatapp.domain.model.User

class UserAdapter(private val itemClick: (user: User) -> Unit) : ListAdapter<User, UserAdapter
    .UserViewHolder>(UserDiffCallback) {

    companion object UserDiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
    inner class UserViewHolder(private val binding : ItemUserBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(user:User){
            binding.tvUserName.text = user.userName
            binding.root.setOnClickListener {
                itemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
      return  UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}