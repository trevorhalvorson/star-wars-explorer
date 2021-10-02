package dev.trev.starwarsexplorer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    @PrimaryKey val uid: String,
    val name: String,
    val url: String
)
