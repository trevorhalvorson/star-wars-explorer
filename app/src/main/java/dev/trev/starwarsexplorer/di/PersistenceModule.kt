package dev.trev.starwarsexplorer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.trev.starwarsexplorer.BuildConfig
import dev.trev.starwarsexplorer.db.SWDatabase
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(produceFile = {
            context.preferencesDataStoreFile(BuildConfig.PREFS_DS_FILE_NAME)
        })
}