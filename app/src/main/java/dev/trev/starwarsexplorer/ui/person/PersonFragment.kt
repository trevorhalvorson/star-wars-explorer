package dev.trev.starwarsexplorer.ui.person

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.trev.starwarsexplorer.R
import dev.trev.starwarsexplorer.databinding.PersonFragmentBinding
import dev.trev.starwarsexplorer.model.Person
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonFragment : Fragment() {
    private val personViewModel: PersonViewModel by viewModels()

    private var _binding: PersonFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PersonFragmentBinding.inflate(inflater, container, false)
        return binding.root
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toggleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.personLoadingProgressBar.show()
        } else {
            binding.personLoadingProgressBar.hide()
        }
    }

    private fun showPerson(person: Person) {
        binding.personNameTv.text = person.name
        binding.personHeightTv.text = getString(R.string.person_height_label, person.height)
        binding.personMassTv.text = getString(R.string.person_mass_label, person.mass)
        binding.personHairColorTv.text =
            getString(R.string.person_hair_color_label, person.hairColor)
        binding.personSkinColorTv.text =
            getString(R.string.person_skin_color_label, person.skinColor)
        binding.personEyeColorTv.text = getString(R.string.person_eye_color_label, person.eyeColor)
        binding.personBirthYearTv.text =
            getString(R.string.person_birth_year_label, person.birthYear)
        binding.personGenderTv.text = getString(R.string.person_gender_label, person.gender)
    }

    private fun showError(exception: Throwable) {
        Snackbar.make(
            requireContext(),
            requireView(),
            exception.localizedMessage,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val TAG = "PersonFragment"
    }
}