package dev.trev.starwarsexplorer.api

import dev.trev.starwarsexplorer.api.response.PeopleResponse
import dev.trev.starwarsexplorer.api.response.PersonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SWApiService {
    @GET("people")
    suspend fun fetchPeople(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): PeopleResponse

    @GET("people/{uid}")
    suspend fun fetchPerson(
        @Path("uid") uid: String,
    ): PersonResponse
}
