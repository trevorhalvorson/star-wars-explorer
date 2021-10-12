package dev.trev.starwarsexplorer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Person(
    @PrimaryKey val uid: String,
    val name: String,
    val url: String,
    val propertiesExpirationTime: Long?,
    val height: String?,
    val mass: String?,
    val hairColor: String?,
    val skinColor: String?,
    val eyeColor: String?,
    val birthYear: String?,
    val gender: String?,
    val homeworld: String?,
) {
    constructor(
        uid: String,
        propertiesExpirationTime: Long?,
        properties: Properties,
    ) : this(
        uid,
        properties.name,
        properties.url,
        propertiesExpirationTime,
        properties.height,
        properties.mass,
        properties.hairColor,
        properties.skinColor,
        properties.eyeColor,
        properties.birthYear,
        properties.gender,
        properties.homeworld,
    )

    data class Properties(
        val name: String,
        val url: String,
        val height: String,
        val mass: String,
        @SerializedName("hair_color")
        val hairColor: String,
        @SerializedName("skin_color")
        val skinColor: String,
        @SerializedName("eye_color")
        val eyeColor: String,
        @SerializedName("birth_year")
        val birthYear: String,
        val gender: String,
        val homeworld: String,
    )
}
