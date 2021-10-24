package dev.trev.starwarsexplorer.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import dev.trev.starwarsexplorer.utils.testPeople
import dev.trev.starwarsexplorer.utils.testPerson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PersonDaoTest {
    private lateinit var db: SWDatabase
    private lateinit var dao: PersonDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, SWDatabase::class.java).build()
        dao = db.personDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertPeopleAndGetAll() = runBlocking {
        db.personDao().insertAll(testPeople)
        val people = dao.getPeople()
        assertThat(people.size, equalTo(testPeople.size))
    }

    @Test
    fun insertPersonAndGetById() = runBlocking {
        db.personDao().insertPerson(testPerson)
        assertThat(dao.getPerson(testPerson.uid).first(), equalTo(testPerson))
    }

}