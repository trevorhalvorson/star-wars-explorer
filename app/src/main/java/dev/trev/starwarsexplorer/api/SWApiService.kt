package dev.trev.starwarsexplorer.api

import dev.trev.starwarsexplorer.api.response.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SWApiService {
    @GET("people")
    suspend fun fetchPeople(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): PeopleResponse
}
