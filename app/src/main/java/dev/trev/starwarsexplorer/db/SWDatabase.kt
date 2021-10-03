package dev.trev.starwarsexplorer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.trev.starwarsexplorer.model.Person
import dev.trev.starwarsexplorer.model.RemoteKey

@Database(
    entities = [Person::class, RemoteKey::class],
    version = 1,
)
abstract class SWDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}