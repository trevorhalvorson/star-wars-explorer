package dev.trev.starwarsexplorer.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import dev.trev.starwarsexplorer.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(key: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE resource = :resource")
    suspend fun remoteKeyByResource(resource: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE resource = :resource")
    suspend fun deleteByResource(resource: String)
}
