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

class PeronDaoTest {
    private lateinit var db: SWDatabase
    private lateinit var dao: PersonDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, SWDatabase::class.java).build()
        dao = db.personDao()

        db.personDao().insertAll(testPeople)
        db.personDao().insertPerson(testPerson)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getPeople() = runBlocking {
        val people = dao.getPeople()
        assertThat(people.size, equalTo(4))
    }

    @Test
    fun getPerson() = runBlocking {
        assertThat(dao.getPerson(testPeople[0].uid).first(), equalTo(testPeople[0]))
        assertThat(dao.getPerson(testPerson.uid).first(), equalTo(testPerson))
    }

}