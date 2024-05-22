package com.mz.chatapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long? = null,
    val userName: String
)