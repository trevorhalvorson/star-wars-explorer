package dev.trev.starwarsexplorer.ui.people

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.trev.starwarsexplorer.R
import dev.trev.starwarsexplorer.model.Person

class PeopleAdapter(private val onClick: (Person) -> Unit) :
    PagingDataAdapter<Person, PeopleAdapter.ViewHolder>(PERSON_COMPARATOR) {

    companion object {
        val PERSON_COMPARATOR = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Person, newItem: Person) =
                oldItem.uid == newItem.uid
        }
    }

    class ViewHolder(view: View, val onClick: (Person) -> Unit) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.person_name_text_view)

        private var person: Person? = null

        init {
            view.setOnClickListener {
                person?.let {
                    onClick(it)
                }
            }
        }

        fun bind(person: Person?) {
            this.person = person
            nameTextView.text = person?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.people_list_item, parent, false),
            onClick
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
