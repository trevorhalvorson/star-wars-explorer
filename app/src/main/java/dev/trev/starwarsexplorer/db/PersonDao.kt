package dev.trev.starwarsexplorer.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.trev.starwarsexplorer.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(people: List<Person>)

    @Insert(onConflict = REPLACE)
    suspend fun insertPerson(person: Person)

    @Query("SELECT * FROM person")
    fun getAllPaged(): PagingSource<Int, Person>

    @Query("SELECT * FROM person")
    suspend fun getAll(): List<Person>

    @Query("SELECT * FROM person WHERE uid = :uid")
    fun getPerson(uid: String): Flow<Person>

    @Query("DELETE FROM person")
    suspend fun deleteAll()
}
