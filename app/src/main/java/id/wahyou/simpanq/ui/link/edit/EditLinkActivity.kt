package id.wahyou.simpanq.ui.link.edit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import id.wahyou.simpanq.R
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.databinding.ActivityEditLinkBinding

@AndroidEntryPoint
class EditLinkActivity : AppCompatActivity() {

    private val binding : ActivityEditLinkBinding by lazy {
        ActivityEditLinkBinding.inflate(layoutInflater)
    }

    private val data : Link? by lazy {
        intent.getParcelableExtra("data")
    }

    private val viewModel: EditLinkViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getData()
        setupViewModel()
        setupListener()
    }

    private fun getData() {
        with(binding){
            etName.setText(data?.title)
            etContent.setText(data?.content)
        }
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
                            updateData()
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

    private fun updateData() {
        with(binding) {
            viewModel.updateLink(Link(
                    data!!.id,
                    etName.text.toString(),
                    etContent.text.toString())
            )
        }
    }

    private fun showMessage(message: String){
        Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show()
    }
}