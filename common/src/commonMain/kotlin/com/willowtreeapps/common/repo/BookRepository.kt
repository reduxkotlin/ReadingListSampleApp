package com.willowtreeapps.common.repo

import kotlinx.serialization.Serializable


interface BookRepository {
    suspend fun search(query: String): GatewayResponse<List<Book>, GenericError>
}
