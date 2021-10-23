package dev.trev.starwarsexplorer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dev.trev.starwarsexplorer.api.SWApiService
import dev.trev.starwarsexplorer.db.SWDatabase
import dev.trev.starwarsexplorer.repository.PeopleRepository

@Module
@InstallIn(ActivityComponent::class)
object PeopleRepositoryModule {
    @Provides
    fun providePeopleRepository(
        db: SWDatabase,
        service: SWApiService
    ): PeopleRepository {
        return PeopleRepository(db, service)
    }

}