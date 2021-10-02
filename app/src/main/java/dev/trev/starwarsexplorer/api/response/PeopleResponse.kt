package dev.trev.starwarsexplorer.api.response

import com.google.gson.annotations.SerializedName
import dev.trev.starwarsexplorer.model.Person

data class PeopleResponse(
    @SerializedName("results")
    val people: List<Person>
)