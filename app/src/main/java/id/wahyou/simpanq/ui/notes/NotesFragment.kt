package id.wahyou.simpanq.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.wahyou.simpanq.databinding.FragmentNotesBinding

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private val binding : FragmentNotesBinding by lazy {
        FragmentNotesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

}