package com.mz.chatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mz.chatapp.databinding.ItemMessageReceivedBinding
import com.mz.chatapp.databinding.ItemMessageSendBinding
import com.mz.chatapp.domain.model.Message
import com.mz.chatapp.presentation.utils.DateUtil


class MessageAdapter(private val userName: String) :
    ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffCallback) {
    companion object MessageDiffCallback : DiffUtil.ItemCallback<Message>() {

        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    private val VIEW_TYPE_USER_MESSAGE_SENT = 10
    private val VIEW_TYPE_USER_MESSAGE_RECEIVED = 11
    override fun getItemViewType(position: Int): Int {
        return getItem(position).let {
            if (it.senderName == userName) {
                VIEW_TYPE_USER_MESSAGE_SENT
            } else {
                VIEW_TYPE_USER_MESSAGE_RECEIVED
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_USER_MESSAGE_SENT -> {
                MessageSentViewHolder(
                    ItemMessageSendBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            else -> {
                MessageReceivedViewHolder(
                    ItemMessageReceivedBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            if (holder is MessageSentViewHolder) {
                holder.bind(it, position)
            } else {
                (holder as MessageReceivedViewHolder).bind(it, position)
            }
        }

    }

    inner class MessageReceivedViewHolder(private val binding: ItemMessageReceivedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message, position: Int) {
            binding.tvMessageReceived.text = message.text
            binding.tvMessageReceivedUser.text = message.senderName
            val date = DateUtil.formatDate(message.sentAt)
            if (position == 0) {
                binding.tvMessageReceivedDate.text = date
                binding.tvMessageReceivedDate.visibility = View.VISIBLE
                return
            }
            val previousDateMessage = DateUtil.formatDate(getItem(position - 1).sentAt)
            if (date != previousDateMessage) {
                binding.tvMessageReceivedDate.text = date
                binding.tvMessageReceivedDate.visibility = View.VISIBLE
            } else {
                binding.tvMessageReceivedDate.visibility = View.GONE

            }
            binding.tvMessageReceivedTime.text = DateUtil.formatTime(message.sentAt)
        }
    }

    inner class MessageSentViewHolder(private val binding: ItemMessageSendBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(message: Message, position: Int) {
            binding.tvMessageSent.text = message.text
            binding.tvMessageSentTime.text = DateUtil.formatTime(message.sentAt)
            val date = DateUtil.formatDate(message.sentAt)
            if (position == 0) {
                binding.tvMessageSentDate.text = date
                binding.tvMessageSentDate.visibility = View.VISIBLE
                return
            }
            val previousDateMessage = DateUtil.formatDate(getItem(position - 1).sentAt)
            if (date != previousDateMessage) {
                binding.tvMessageSentDate.text = date
                binding.tvMessageSentDate.visibility = View.VISIBLE
            } else {
                binding.tvMessageSentDate.visibility = View.GONE
            }
        }
    }


}