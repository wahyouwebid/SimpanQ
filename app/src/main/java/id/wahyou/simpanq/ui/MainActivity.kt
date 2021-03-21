package id.wahyou.simpanq.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import id.wahyou.simpanq.R
import id.wahyou.simpanq.databinding.ActivityMainBinding
import id.wahyou.simpanq.ui.link.LinkFragment
import id.wahyou.simpanq.ui.notes.NotesFragment
import id.wahyou.simpanq.ui.plan.PlanFragment
import id.wahyou.simpanq.ui.setting.SettingFragment
import id.wahyou.simpanq.ui.task.TaskFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupFragment(LinkFragment())
        setUpTabBar()
    }

    private fun setUpTabBar() {
        with(binding) {
            bottomNavBar.setItemSelected(R.id.menu_link)
            bottomNavBar.setOnItemSelectedListener {
                when (it) {
                    R.id.menu_planing -> setupFragment(PlanFragment())
                    R.id.menu_notes -> setupFragment(NotesFragment())
                    R.id.menu_link -> setupFragment(LinkFragment())
                    R.id.menu_task -> setupFragment(TaskFragment())
                    R.id.menu_setting -> setupFragment(SettingFragment())
                }
            }
        }
    }

    private fun setupFragment(fragment : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentLayout, fragment)
        transaction.commit()
    }
}