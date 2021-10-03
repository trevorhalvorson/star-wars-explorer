package dev.trev.starwarsexplorer.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.trev.starwarsexplorer.model.Person

@Dao
interface PersonDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(people: List<Person>)

    @Query("SELECT * FROM person")
    fun getAll(): PagingSource<Int, Person>

    @Query("DELETE FROM person")
    suspend fun deleteAll()
}
