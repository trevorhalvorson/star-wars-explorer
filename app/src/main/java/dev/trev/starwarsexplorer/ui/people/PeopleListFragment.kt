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
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.trev.starwarsexplorer.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PeopleListFragment : Fragment() {

    companion object {
        const val TAG = "PeopleListFragment"

        fun newInstance() = PeopleListFragment()
    }

    private val peopleListViewModel: PeopleListViewModel by viewModels()

    private val peopleAdapter = PeopleAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.people_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout: SwipeRefreshLayout = view.findViewById(R.id.people_list_srl)
        swipeRefreshLayout.setOnRefreshListener {
            peopleAdapter.refresh()
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.people_list_rv)
        recyclerView.adapter = peopleAdapter

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

    private fun showError(exception: Throwable) {
        Snackbar.make(
            requireContext(),
            requireView(),
            exception.localizedMessage,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
