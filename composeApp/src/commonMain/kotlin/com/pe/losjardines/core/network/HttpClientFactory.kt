package com.pe.losjardines.core.network

import com.pe.losjardines.BuildKonfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientFactory {
    fun createClient(): HttpClient{
        return HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BuildKonfig.BASE_URL
                }
                header("User-Agent", "Mozilla/5.0 (Android 13; Mobile; rv:109.0) Gecko/20100101 Firefox/115.0")
                header("Accept", "application/json")
            }
        }
    }
}