package dev.trev.starwarsexplorer.ui.person

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.trev.starwarsexplorer.R
import dev.trev.starwarsexplorer.model.Person
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonFragment : Fragment() {

    companion object {
        const val TAG = "PersonFragment"
    }

    private val args: PersonFragmentArgs by navArgs()
    private val personViewModel: PersonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                personViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is PersonUiState.Loading -> {
                            toggleLoading(true)
                        }
                        is PersonUiState.Success -> {
                            toggleLoading(false)
                            showPerson(uiState.person)
                        }
                        is PersonUiState.Error -> {
                            toggleLoading(false)
                            showError(uiState.exception)
                        }
                    }
                }
            }
        }

        val uid = args.uidArg
        personViewModel.setUid(uid)
    }

    private fun toggleLoading(isLoading: Boolean) {
        Log.i(TAG, "isLoading: $isLoading")
    }

    private fun showPerson(person: Person) {
        Log.i(TAG, person.toString())
    }

    private fun showError(exception: Throwable) {
        Snackbar.make(
            requireContext(),
            requireView(),
            exception.localizedMessage,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}