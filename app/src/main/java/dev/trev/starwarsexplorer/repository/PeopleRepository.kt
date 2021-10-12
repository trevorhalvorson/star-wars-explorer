package dev.trev.starwarsexplorer.repository

import android.text.format.DateUtils
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.trev.starwarsexplorer.api.SWApiService
import dev.trev.starwarsexplorer.db.SWDatabase
import dev.trev.starwarsexplorer.model.Person
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val db: SWDatabase,
    private val service: SWApiService,
) {
    companion object {
        const val RESOURCE = "people"
    }

    @OptIn(ExperimentalPagingApi::class)
    fun people(pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(db, service, RESOURCE),
    ) {
        db.personDao().getAllPaged()
    }.flow

    suspend fun person(uid: String): Flow<Person> {
        val expirationTime = db.personDao().getPersonPropertiesExpirationTime(uid)
        if (expirationTime == null || System.currentTimeMillis() > expirationTime) {
            val response = service.fetchPerson(uid)
            val person = Person(
                response.result.uid,
                System.currentTimeMillis() + DateUtils.DAY_IN_MILLIS,
                response.result.properties
            )
            db.personDao().insertPerson(person)
        }
        return db.personDao().getPerson(uid)
    }
}
