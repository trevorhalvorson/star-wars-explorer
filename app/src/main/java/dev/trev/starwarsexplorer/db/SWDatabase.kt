package dev.trev.starwarsexplorer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.trev.starwarsexplorer.model.Person

@Database(entities = [Person::class], version = 1)
abstract class SWDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}