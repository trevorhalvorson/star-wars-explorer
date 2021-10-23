package dev.trev.starwarsexplorer.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.trev.starwarsexplorer.databinding.NetworkStateItemBinding

class NetworkLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<NetworkLoadStateAdapter.LoadStateViewHolder>() {
    class LoadStateViewHolder(
        private val binding: NetworkStateItemBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private val retry = binding.retryButton
            .also {
                it.setOnClickListener { retryCallback() }
            }

        fun bind(loadState: LoadState) {
            binding.progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible =
                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            binding.errorMsg.text = (loadState as? LoadState.Error)?.error?.message
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder {
        val binding =
            NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}
