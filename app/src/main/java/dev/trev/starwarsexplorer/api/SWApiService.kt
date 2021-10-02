package dev.trev.starwarsexplorer.api

import dev.trev.starwarsexplorer.api.response.PeopleResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface SWApiService {
    @GET("people")
    suspend fun fetchPeopleResponse(): PeopleResponse
}
