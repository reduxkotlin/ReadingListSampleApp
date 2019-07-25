package com.willowtreeapps.common.repo

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlinx.serialization.json.Json
import kotlin.coroutines.CoroutineContext

open class KtorOpenBookRepository(private val networkContext: CoroutineContext) : BookRepository, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = networkContext + Job()

    companion object {
        private const val baseUrl = "https://openlibrary.org"
        private const val SEARCH_PATH = "search.json"
    }


    override suspend fun search(query: String): GatewayResponse<List<Book>, GenericError> {
        return try {
            val response: HttpResponse = client.get {
                apiUrl(SEARCH_PATH)
                parameter("q", query)
            }
            val jsonBody = response.readText()
            val bookList = Json.nonstrict.parse(BooksResponse.serializer(), jsonBody)
            GatewayResponse.createSuccess(bookList.docs, 200, "Success")
        } catch (e: Exception) {
            com.willowtreeapps.common.Logger.d("FAILURE FETCHING BOOKS:  ${e.message}")
            GatewayResponse.createError(GenericError(e.message
                    ?: "Failure"), 500, e.message ?: "failure")
        }
    }

    private val client by lazy {
        return@lazy try {
            HttpClient {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(Json.nonstrict).apply {
//                        setMapper()
                    }
                }
                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.ALL
                }
            }


        } catch (e: Exception) {
            throw RuntimeException("Error initialization: ${e.message}")
        }
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        header(HttpHeaders.CacheControl, io.ktor.client.utils.CacheControl.MAX_AGE)
        url {
            takeFrom(baseUrl)
            encodedPath = path
        }
    }
}

@Serializable
data class Book(
        val cover_edition_key: String? = null,
//        val edition_key: String? = null,
        @SerialName("author_name")
        val authorName: List<String> = listOf("unknown"),
        @SerialName("title_suggest")
        val title: String) {

    val openLibraryId: String
        get() = cover_edition_key /*?: edition_key */ ?: ""//throw IllegalArgumentException("No key found for item in response")
    val coverUrl: String
        get() = "https://covers.openlibrary.org/b/olid/$openLibraryId-M.jpg?default=false"
    val largeCoverUrl: String
        get() = "https://covers.openlibrary.org/b/olid/$openLibraryId-L.jpg?default=false"
}


@Serializable
class BooksResponse(val docs: List<Book>)
