package dev.trev.starwarsexplorer.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dev.trev.starwarsexplorer.api.SWApiService
import dev.trev.starwarsexplorer.db.PersonDao
import dev.trev.starwarsexplorer.db.SWDatabase
import dev.trev.starwarsexplorer.repository.PeopleRepository

@Module
@InstallIn(ActivityComponent::class)
object PeopleRepositoryModule {
    @Provides
    fun providePeopleRepository(
        personDao: PersonDao,
        service: SWApiService,
        dataStore: DataStore<Preferences>,
    ): PeopleRepository {
        return PeopleRepository(personDao, service, dataStore)
    }

}