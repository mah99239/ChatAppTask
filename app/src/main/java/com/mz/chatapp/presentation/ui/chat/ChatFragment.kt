package com.mz.chatapp.presentation.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.mz.chatapp.databinding.FragmentChatBinding
import com.mz.chatapp.presentation.adapter.MessageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
/**
 * A fragment representing a chat screen.
 */
@AndroidEntryPoint
class ChatFragment : Fragment() {
   private lateinit var binding :FragmentChatBinding
    private val viewModel by activityViewModels<ChatViewModel>()
    private lateinit var adapter: MessageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setup()
    }
    private fun setup() {
        viewModel.fetchMessage()
        setupAdapter()
        observerMessages()
        setupMessageListener()
        setupSendClick()
    }
    private fun setupAdapter() {
        val   name = requireActivity().intent.getStringExtra("username").toString()
        adapter = MessageAdapter(name)
        binding.recyclerView.adapter = adapter
    }
    private fun observerMessages() {
        viewModel.messages.observe(viewLifecycleOwner) {
            adapter.submitList(it)

            lifecycleScope.launch {
                delay(100)
                binding.recyclerView.scrollToPosition(adapter.currentList.size - 1)
            }

        }
    }

    private fun setupMessageListener() {
        binding.messageEdit.doAfterTextChanged {text ->
            val message: String = text.toString().trim()
            if (message.isEmpty()) {
                resetMessage()
            } else {
                binding.btnSend.visibility = View.VISIBLE
            }
        }
    }

    private fun setupSendClick() {
        binding.btnSend.setOnClickListener {
            viewModel.sentMessage(binding.messageEdit.text.toString().trim())
            resetMessage()
        }
    }


    private fun resetMessage() {
        binding.messageEdit.text.clear()
        binding.btnSend.visibility = View.INVISIBLE

    }

}