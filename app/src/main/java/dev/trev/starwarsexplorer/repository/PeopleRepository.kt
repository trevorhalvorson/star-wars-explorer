package dev.trev.starwarsexplorer.repository

import android.text.format.DateUtils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.trev.starwarsexplorer.api.SWApiService
import dev.trev.starwarsexplorer.db.PersonDao
import dev.trev.starwarsexplorer.model.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val personDao: PersonDao,
    private val service: SWApiService,
    private val dataStore: DataStore<Preferences>
) {
    @OptIn(ExperimentalPagingApi::class)
    fun people(pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PeopleRemoteMediator(personDao, service, dataStore),
    ) {
        personDao.getAllPaged()
    }.flow

    suspend fun person(uid: String): Flow<Person> {
        val expTime = personDao.getPerson(uid).first().propertiesExpirationTime
        if (expTime == null || System.currentTimeMillis() > expTime) {
            val response = service.fetchPerson(uid)
            val person = Person(
                response.result.uid,
                System.currentTimeMillis() + DateUtils.DAY_IN_MILLIS,
                response.result.properties
            )
            personDao.insertPerson(person)
        }
        return personDao.getPerson(uid)
    }
}
