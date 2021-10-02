package dev.trev.starwarsexplorer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.trev.starwarsexplorer.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(person: Person)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(people: List<Person>)

    @Query("SELECT COUNT(*) FROM person")
    suspend fun getCount(): Int

    @Query("SELECT * FROM person")
    fun getAll(): Flow<List<Person>>
}
