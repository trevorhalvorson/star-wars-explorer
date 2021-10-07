package dev.trev.starwarsexplorer.ui.person

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import dev.trev.starwarsexplorer.R

class PersonFragment : Fragment() {

    companion object {
        const val TAG = "PersonFragment"
    }

    private val args: PersonFragmentArgs by navArgs()

    private lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        val uid = args.uidArg
        val tv = view.findViewById<TextView>(R.id.person_tv)
        tv.text = uid
    }

}