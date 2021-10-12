package dev.trev.starwarsexplorer.ui.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
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

    private lateinit var loadingProgressBar: ContentLoadingProgressBar
    private lateinit var nameTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var massTextView: TextView
    private lateinit var hairColorTextView: TextView
    private lateinit var skinColorTextView: TextView
    private lateinit var eyeColorTextView: TextView
    private lateinit var birthYearTextView: TextView
    private lateinit var genderTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = view.findViewById(R.id.person_loading_progress_bar)
        nameTextView = view.findViewById(R.id.person_name_tv)
        heightTextView = view.findViewById(R.id.person_height_tv)
        massTextView = view.findViewById(R.id.person_mass_tv)
        hairColorTextView = view.findViewById(R.id.person_hair_color_tv)
        skinColorTextView = view.findViewById(R.id.person_skin_color_tv)
        eyeColorTextView = view.findViewById(R.id.person_eye_color_tv)
        birthYearTextView = view.findViewById(R.id.person_birth_year_tv)
        genderTextView = view.findViewById(R.id.person_gender_tv)

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
        if (isLoading) {
            loadingProgressBar.show()
        } else {
            loadingProgressBar.hide()
        }
    }

    private fun showPerson(person: Person) {
        nameTextView.text = person.name
        heightTextView.text = getString(R.string.person_height_label, person.height)
        massTextView.text = getString(R.string.person_mass_label, person.mass)
        hairColorTextView.text = getString(R.string.person_hair_color_label, person.hairColor)
        skinColorTextView.text = getString(R.string.person_skin_color_label, person.skinColor)
        eyeColorTextView.text = getString(R.string.person_eye_color_label, person.eyeColor)
        birthYearTextView.text = getString(R.string.person_birth_year_label, person.birthYear)
        genderTextView.text = getString(R.string.person_gender_label, person.gender)
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