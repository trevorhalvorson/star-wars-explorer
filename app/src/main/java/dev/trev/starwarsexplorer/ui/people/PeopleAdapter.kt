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

class PeopleAdapter :
    PagingDataAdapter<Person, PeopleAdapter.ViewHolder>(PERSON_COMPARATOR) {

    companion object {
        val PERSON_COMPARATOR = object : DiffUtil.ItemCallback<Person>() {
            override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem

            override fun areContentsTheSame(oldItem: Person, newItem: Person) =
                oldItem.uid == newItem.uid
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.person_name_text_view)

        fun bind(person: Person?) {
            nameTextView.text = person?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.people_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
