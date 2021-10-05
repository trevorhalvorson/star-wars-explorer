package dev.trev.starwarsexplorer.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dev.trev.starwarsexplorer.api.SWApiService
import dev.trev.starwarsexplorer.db.SWDatabase
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val db: SWDatabase,
    private val service: SWApiService,
) {
    companion object {
        const val RESOURCE = "people"
    }

    @OptIn(ExperimentalPagingApi::class)
    fun people(pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(db, service, RESOURCE),
    ) {
        db.personDao().getAllPaged()
    }.flow
}
