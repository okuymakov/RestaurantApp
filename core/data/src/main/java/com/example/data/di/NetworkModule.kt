package com.example.data.di

import android.util.Log
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton


const val API_URL = "https://run.mocky.io/v3/"
const val NETWORK_TIME_OUT = 30_000

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                url(API_URL)
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

                engine {
                    connectTimeout = NETWORK_TIME_OUT
                    socketTimeout = NETWORK_TIME_OUT
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("http log: ", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}