package dev.trev.starwarsexplorer.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.trev.starwarsexplorer.R

class NetworkLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<NetworkLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(
        parent: ViewGroup,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.network_state_item, parent, false)
    ) {
        private val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
        private val errorMsg = itemView.findViewById<TextView>(R.id.error_msg)
        private val retry = itemView.findViewById<Button>(R.id.retry_button)
            .also {
                it.setOnClickListener { retryCallback() }
            }

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
            retry.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            errorMsg.text = (loadState as? LoadState.Error)?.error?.message
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}
