package com.mz.chatapp.presentation.ui.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mz.chatapp.R
import com.mz.chatapp.databinding.FragmentUserBinding
import com.mz.chatapp.presentation.adapter.UserAdapter
import com.mz.chatapp.presentation.ui.chat.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
  private var _binding: FragmentUserBinding? = null
    val binding get() = _binding!!
    // TODO: handle all user can be managed by view model
    private val viewModel by activityViewModels<ChatViewModel>()
    private val userAdapter = UserAdapter{
        findNavController()
            .navigate(R.id.action_user_to_chatFragment)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setup()
    }

    private fun setup() {
        setupAdapter()
        setupObserver()
    }
    private fun setupAdapter() {
        binding.recyclerView.adapter = userAdapter
    }

    private fun setupObserver() {
        /* viewModel.users.observe(viewLifecycleOwner) {
                   userAdapter.submitList(it)
               }*/
    }

}