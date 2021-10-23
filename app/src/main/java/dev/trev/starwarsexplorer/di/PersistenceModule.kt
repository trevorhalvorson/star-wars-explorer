package dev.trev.starwarsexplorer.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.trev.starwarsexplorer.BuildConfig
import dev.trev.starwarsexplorer.db.SWDatabase

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    fun provideSWDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            SWDatabase::class.java,
            BuildConfig.SW_DB_NAME,
        ).build()

    @Provides
    fun providePersonDao(db: SWDatabase) = db.personDao()
}