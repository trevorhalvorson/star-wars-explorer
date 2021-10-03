package dev.trev.starwarsexplorer.api.response

import com.google.gson.annotations.SerializedName
import dev.trev.starwarsexplorer.model.Person

data class PeopleResponse(
    val results: List<Person>,
    val next: String?,
    @SerializedName("total_pages")
    val totalPages: Int,
)