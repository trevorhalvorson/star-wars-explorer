package dev.trev.starwarsexplorer.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.trev.starwarsexplorer.model.Person
import dev.trev.starwarsexplorer.repository.PeopleRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(private val peopleRepository: PeopleRepository) :
    ViewModel() {

    companion object {
        const val PAGE_SIZE = 10
    }

    private val _uiState = MutableStateFlow(PeopleListUiState.Success(PagingData.empty()))
    val uiState: StateFlow<PeopleListUiState> = _uiState

    init {
        viewModelScope.launch {
            peopleRepository.people(PAGE_SIZE)
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _uiState.value = PeopleListUiState.Success(pagingData)
                }
        }
    }
}

sealed class PeopleListUiState {
    data class Success(val pagingData: PagingData<Person>) : PeopleListUiState()
    data class Error(val exception: Throwable) : PeopleListUiState()
}
