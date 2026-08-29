package com.accidentaldeveloper.briefly.network

import com.accidentaldeveloper.briefly.api.ApiConstant
import com.accidentaldeveloper.briefly.datastore.DataStoreManager
import com.accidentaldeveloper.briefly.model.ApiErrorResponse
import com.accidentaldeveloper.briefly.model.NewsApiException
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private val NetworkJson = Json {
    ignoreUnknownKeys = true
}

class BrieflyClient(private val dataStoreManager: DataStoreManager){
    val newsApiClient = HttpClient{
        expectSuccess = true
        install(ContentNegotiation){
            json(NetworkJson)
        }

        install(HttpTimeout){
            socketTimeoutMillis = 30000L
            requestTimeoutMillis = 30000L
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("ktor log : $message")
                }

            }
        }

        install(DefaultRequest){
            url(ApiConstant.BASE_URL)
            contentType(ContentType.Application.Json)
        }

        install(BrieflyAuthPlugin){
            apiKeyProvider = dataStoreManager::getApiKey
        }

        HttpResponseValidator {
            handleResponseExceptionWithRequest { cause, request ->
                val responseException = cause as? ResponseException?:return@handleResponseExceptionWithRequest
                val errorBody = responseException.response.bodyAsText()
                val newsApiError = NetworkJson.decodeFromString<ApiErrorResponse>(errorBody)
                throw NewsApiException(
                    statusCode = newsApiError.status,
                    message = newsApiError.message,
                )
            }
        }
    }
}


private class BrieflyAuthPluginConfig{
    var apiKeyProvider: suspend () -> String = { "" }
}

private val BrieflyAuthPlugin = createClientPlugin(
    name = "Briefly",
    createConfiguration = {BrieflyAuthPluginConfig()}

){
    val apiKeyProvider = pluginConfig.apiKeyProvider
    onRequest { request, _ ->
        val apiKey = apiKeyProvider()
        if (apiKey.isNotEmpty()) {
            request.url.parameters.append("apiKey", apiKey)
        }
    }
}