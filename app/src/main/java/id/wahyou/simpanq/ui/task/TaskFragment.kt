package id.wahyou.simpanq.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.wahyou.simpanq.databinding.FragmentTaskBinding

@AndroidEntryPoint
class TaskFragment : Fragment() {

    private val binding : FragmentTaskBinding by lazy {
        FragmentTaskBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}