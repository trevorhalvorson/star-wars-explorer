package dev.trev.starwarsexplorer.repository

import dev.trev.starwarsexplorer.api.SWApiService
import dev.trev.starwarsexplorer.db.PersonDao
import dev.trev.starwarsexplorer.model.Person
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val service: SWApiService,
    private val personDao: PersonDao
) {

    suspend fun loadPeople(forceRefresh: Boolean) {
        val isEmpty = personDao.getCount() == 0
        if (forceRefresh || isEmpty) {
            val response = service.fetchPeopleResponse()
            personDao.insertAll(response.people)
        }
    }

    fun getPeople(): Flow<List<Person>> {
        return personDao.getAll()
    }
}
