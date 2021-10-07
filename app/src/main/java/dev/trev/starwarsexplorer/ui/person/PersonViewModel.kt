package dev.trev.starwarsexplorer.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.trev.starwarsexplorer.model.Person
import dev.trev.starwarsexplorer.repository.PeopleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val peopleRepository: PeopleRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<PersonUiState>(PersonUiState.Loading)
    val uiState: StateFlow<PersonUiState> = _uiState

    fun setUid(uid: String) {
        viewModelScope.launch {
            _uiState.value = PersonUiState.Loading
            peopleRepository.person(uid)
                .collect { person ->
                    _uiState.value = PersonUiState.Success(person)
                }
        }
    }
}

sealed class PersonUiState {
    object Loading : PersonUiState()
    data class Success(val person: Person) : PersonUiState()
    data class Error(val exception: Throwable) : PersonUiState()
}
