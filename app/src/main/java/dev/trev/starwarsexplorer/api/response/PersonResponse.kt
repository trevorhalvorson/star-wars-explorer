package dev.trev.starwarsexplorer.api.response

import dev.trev.starwarsexplorer.model.Person

data class PersonResponse(
    val message: String,
    val result: Result,
) {
    data class Result(
        val properties: Person.Properties,
        val description: String,
        val uid: String,
    )
}