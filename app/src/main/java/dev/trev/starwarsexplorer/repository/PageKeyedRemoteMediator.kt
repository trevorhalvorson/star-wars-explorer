package dev.trev.starwarsexplorer.repository

import androidx.paging.*
import androidx.paging.LoadType.*
import androidx.room.withTransaction
import dev.trev.starwarsexplorer.api.SWApiService
import dev.trev.starwarsexplorer.db.SWDatabase
import dev.trev.starwarsexplorer.model.Person
import dev.trev.starwarsexplorer.model.RemoteKey
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val db: SWDatabase,
    private val service: SWApiService,
    private val resource: String,
) : RemoteMediator<Int, Person>() {
    private val personDao = db.personDao()
    private val remoteKeyDao = db.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Person>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                REFRESH -> 1
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.remoteKeyByResource(resource)
                    }

                    // If we receive `null` for APPEND, that means we have reached the end of
                    // pagination and there are no more items to load.
                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    val nextPage = remoteKey.nextPageKey.toHttpUrlOrNull()
                        ?.queryParameter("page")
                        ?.toInt()
                        ?: return MediatorResult.Error(IOException("Unable to get next page"))
                    nextPage
                }
            }

            val data = service.fetchPeople(
                page = loadKey,
                limit = state.config.pageSize,
            )

            db.withTransaction {
                if (loadType == REFRESH) {
                    personDao.deleteAll()
                    remoteKeyDao.deleteByResource(resource)
                }

                remoteKeyDao.insert(RemoteKey(resource, data.next))
                personDao.insertAll(data.results)
            }

            return MediatorResult.Success(endOfPaginationReached = loadKey == data.totalPages)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}