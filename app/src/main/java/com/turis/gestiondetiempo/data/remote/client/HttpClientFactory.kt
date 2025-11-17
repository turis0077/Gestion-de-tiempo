package com.turis.gestiondetiempo.data.remote.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    @Volatile
    private var INSTANCE: HttpClient? = null

    fun create(): HttpClient {
        return INSTANCE ?: synchronized(this) {
            val instance = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }

                install(Logging) {
                    level = LogLevel.ALL
                    logger = Logger.ANDROID
                }

                defaultRequest {
                    contentType(ContentType.Application.Json)
                }
            }
            INSTANCE = instance
            instance
        }
    }
}