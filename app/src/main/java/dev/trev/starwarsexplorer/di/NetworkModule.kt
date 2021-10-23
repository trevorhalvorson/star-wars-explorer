package dev.trev.starwarsexplorer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.trev.starwarsexplorer.BuildConfig
import dev.trev.starwarsexplorer.api.SWApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideOkHttpInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideSWApiService(client: OkHttpClient): SWApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SWAPI_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SWApiService::class.java)
    }
}