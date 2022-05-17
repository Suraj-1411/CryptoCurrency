package com.example.cryptocurrency.data.service

import com.example.cryptocurrency.data.models.CryptoResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import kotlin.jvm.Throws

class RemoteRepository @Inject constructor(private val service: RemoteService) {

    @Throws(Exception::class)
    suspend fun getData():CryptoResponse{
        return service.httpClient.get{
            url("https://api.coincap.io/v2/assets")
        }.body()
    }
}