package dev.trev.starwarsexplorer.ui.people

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.trev.starwarsexplorer.databinding.PeopleListFragmentBinding
import dev.trev.starwarsexplorer.model.Person
import dev.trev.starwarsexplorer.ui.common.NetworkLoadStateAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PeopleListFragment : Fragment() {

    companion object {
        const val TAG = "PeopleListFragment"
    }

    private val peopleListViewModel: PeopleListViewModel by viewModels()

    private var _binding: PeopleListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PeopleListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val peopleAdapter = PeopleAdapter { person -> onPersonClicked(person) }

        val swipeRefreshLayout = binding.peopleListSrl
        swipeRefreshLayout.setOnRefreshListener {
            peopleAdapter.refresh()
        }

        val recyclerView = binding.peopleListRv
        recyclerView.adapter =
            peopleAdapter.withLoadStateFooter(NetworkLoadStateAdapter(peopleAdapter::retry))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                peopleListViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is PeopleListUiState.Success -> {
                            swipeRefreshLayout.isRefreshing = false
                            peopleAdapter.submitData(uiState.pagingData)
                        }
                        is PeopleListUiState.Error -> {
                            swipeRefreshLayout.isRefreshing = false
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

    private fun onPersonClicked(person: Person) {
        val action =
            PeopleListFragmentDirections
                .actionPeopleListFragmentToPersonFragment(person.uid)
        view?.findNavController()?.navigate(action)
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
