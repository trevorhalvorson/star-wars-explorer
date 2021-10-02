package dev.trev.starwarsexplorer.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.trev.starwarsexplorer.model.Person
import dev.trev.starwarsexplorer.repository.PeopleRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(private val peopleRepository: PeopleRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(PeopleListUiState.Success(emptyList()))
    val uiState: StateFlow<PeopleListUiState> = _uiState

    init {
        viewModelScope.launch {
            async {
                peopleRepository.loadPeople(false)
            }
            peopleRepository.getPeople()
                .collect { people ->
                    _uiState.value = PeopleListUiState.Success(people)
                }
        }
    }
}

sealed class PeopleListUiState {
    data class Success(val people: List<Person>) : PeopleListUiState()
    data class Error(val exception: Throwable) : PeopleListUiState()
}
