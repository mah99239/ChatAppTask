package com.mz.chatapp.presentation.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mz.chatapp.domain.repo.ChatRepository
import com.mz.chatapp.domain.model.Message
import com.mz.chatapp.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository, private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _messages :MutableLiveData<List<Message>> = MutableLiveData()
    val messages get() = _messages
    init {
        connect()
    }
   private fun connect(){
        viewModelScope.launch {
            chatRepository.connect()
        }
        this.savedStateHandle.get<String>("username")?.let { userName ->
            chatRepository.userName = userName
        }

    }

    fun fetchMessage(){
            viewModelScope.launch {
                chatRepository.getMessages().collect {
                    _messages.value = it
            }
        }
    }
    fun sentMessage(message:String){
        this.savedStateHandle.get<String>("username")?.let {
            viewModelScope.launch {
                chatRepository.sendMessage(message,it)
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        chatRepository.disconnect()
    }
}