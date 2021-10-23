package dev.trev.starwarsexplorer.ui.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.trev.starwarsexplorer.databinding.PeopleListItemBinding
import dev.trev.starwarsexplorer.model.Person

class PeopleAdapter(
    private val onClick: (Person) -> Unit,
) : PagingDataAdapter<Person, PeopleAdapter.ViewHolder>(PERSON_COMPARATOR) {
    class ViewHolder(private val binding: PeopleListItemBinding, val onClick: (Person) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private var person: Person? = null

        init {
            binding.root.setOnClickListener {
                person?.let {
                    onClick(it)
                }
            }
        }

        fun bind(p: Person?) {
            person = p
            binding.peopleListItemNameTextView.text = person?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PeopleListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val PERSON_COMPARATOR = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Person, newItem: Person) =
                oldItem.uid == newItem.uid
        }
    }
}
