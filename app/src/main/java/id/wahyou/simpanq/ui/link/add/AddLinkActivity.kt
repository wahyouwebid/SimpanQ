package id.wahyou.simpanq.ui.link.add

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import id.wahyou.simpanq.R
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.databinding.ActivityAddLinkBinding

@AndroidEntryPoint
class AddLinkActivity : AppCompatActivity() {

    private val binding : ActivityAddLinkBinding by lazy {
        ActivityAddLinkBinding.inflate(layoutInflater)
    }

    private val viewModel: AddLinkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViewModel()
        setupListener()
    }

    private fun setupViewModel(){
        viewModel.state.observe(this, {
            finish()
        })
    }

    private fun setupListener() {
        with(binding){
            btnSave.setOnClickListener {
                if(etContent.text.isNotEmpty()){
                    if (
                        etContent.text.toString().contains("http://") ||
                        etContent.text.toString().contains("https://")
                    ) {
                        if(etContent.text.toString().startsWith("http://") ||
                            etContent.text.toString().startsWith("https://")
                        ) {
                            saveData()
                        } else {
                            showMessage(getString(R.string.title_link_failed))
                        }
                    } else {
                        showMessage(getString(R.string.title_link_failed))
                    }
                }else {
                    showMessage(getString(R.string.title_is_not_empty))
                }
            }
            imgBack.setOnClickListener { finish() }
        }
    }

    private fun saveData() {
        with(binding) {
            viewModel.addLink(
                Link(0,
                etName.text.toString(),
                etContent.text.toString())
            )
        }
    }

    private fun showMessage(message: String){
        Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show()
    }
}