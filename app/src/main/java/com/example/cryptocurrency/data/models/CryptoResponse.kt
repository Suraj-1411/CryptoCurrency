package com.example.cryptocurrency.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CryptoResponse(
    val `data`: List<Data> = arrayListOf(),
    val timestamp: Long?=null
)