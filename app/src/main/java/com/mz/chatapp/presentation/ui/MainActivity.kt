package com.mz.chatapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mz.chatapp.R
import com.mz.chatapp.presentation.ui.chat.ChatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleInputUser()
    }

    private fun handleInputUser() {
        val editText = findViewById<EditText>(R.id.editText)
        findViewById<Button>(R.id.enterBtn).setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("username", editText.text.toString().trim())
            startActivity(intent)
        }
    }
}