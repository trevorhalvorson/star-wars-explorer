package dev.trev.starwarsexplorer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.trev.starwarsexplorer.BuildConfig
import dev.trev.starwarsexplorer.model.Person

@Database(
    entities = [Person::class],
    version = BuildConfig.SW_DB_VERSION,
)
abstract class SWDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}