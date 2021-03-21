package id.wahyou.simpanq.ui.link

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import id.wahyou.simpanq.R
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.databinding.BottomsheetLinkBinding
import id.wahyou.simpanq.databinding.CustomDialogBinding
import id.wahyou.simpanq.databinding.FragmentLinkBinding
import id.wahyou.simpanq.ui.link.add.AddLinkActivity
import id.wahyou.simpanq.ui.link.edit.EditLinkActivity


@AndroidEntryPoint
class LinkFragment : Fragment() {

    private val binding : FragmentLinkBinding by lazy {
        FragmentLinkBinding.inflate(layoutInflater)
    }

    private val bottomSheetDialog : BottomSheetDialog by lazy {
        BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
    }

    private val viewModel : LinkViewModel by viewModels()

    private val adapter : LinkAdapter by lazy {
        LinkAdapter{ item -> showDetail(item)}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupViewModel()
        setupListener()
    }

    private fun setupAdapter(){
        with(binding){
            rvLink.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
            }
        }
    }

    private fun setupViewModel(){
        viewModel.getState.observe(viewLifecycleOwner, { link ->
            if (link.isEmpty()) {
                showComponent(true)
            } else {
                adapter.setData(link)
                showComponent(false)
            }
        })

        viewModel.getData()
        viewModel.setupSearch(binding.etSearch)

        viewModel.deleteState.observe(viewLifecycleOwner, {
            viewModel.getData()
            bottomSheetDialog.dismiss()
        })
    }

    private fun setupListener(){
        with(binding) {
            btnAdd.setOnClickListener {
                startActivity(Intent(requireContext(), AddLinkActivity::class.java))
            }
            fabAdd.setOnClickListener {
                startActivity(Intent(requireContext(), AddLinkActivity::class.java))
            }
        }
    }

    private fun showComponent(isShow: Boolean){
        with(binding) {
            if(isShow) {
                imgBackground.visibility = View.VISIBLE
                btnAdd.visibility = View.VISIBLE
                rvLink.visibility = View.GONE
                fabAdd.visibility = View.GONE
                etSearch.visibility = View.GONE
            }else {
                imgBackground.visibility = View.GONE
                btnAdd.visibility = View.GONE
                rvLink.visibility = View.VISIBLE
                fabAdd.visibility = View.VISIBLE
                etSearch.visibility = View.VISIBLE
            }
        }
    }

    private fun showDetail(data: Link) {
        val binding = BottomsheetLinkBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)
        with(binding) {
            tvOpen.setOnClickListener { openLink(data) }
            tvEdit.setOnClickListener { editLink(data) }
            tvCopy.setOnClickListener { copyLink(data.content) }
            tvShare.setOnClickListener { shareLink(data) }
            tvDelete.setOnClickListener { deleteLink(data) }
        }
        bottomSheetDialog.show()
    }

    private fun openLink(data: Link){
        bottomSheetDialog.dismiss()
        startActivity(Intent().also {
            it.action = Intent.ACTION_VIEW
            it.data = Uri.parse(data.content)
        })
    }

    private fun editLink(data: Link){
        bottomSheetDialog.dismiss()
        startActivity(Intent(requireContext(), EditLinkActivity::class.java).also {
            it.putExtra("data", data)
        })
    }

    private fun copyLink (link: CharSequence){
        val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
        val clip = ClipData.newPlainText("label",link)
        clipboard?.setPrimaryClip(clip)
        Toasty.success(requireContext(),
            R.string.title_link_copy, Toast.LENGTH_SHORT, true).show()
    }

    private fun shareLink(data: Link){
        startActivity(Intent.createChooser(Intent(),"Share To:").also {
            it.action = Intent.ACTION_SEND
            it.putExtra(Intent.EXTRA_TEXT,data.content)
            it.type="text/plain"
        })
    }

    private fun deleteLink(data: Link){
        val binding = CustomDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false)
        dialog.setContentView(binding.root)

        with(binding){
            tvTitle.text = getString(R.string.title_dialog_delete_link)
            tvBody.text = getString(R.string.title_dialog_body_link)
            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            btnDelete.setOnClickListener {
                dialog.dismiss()
                viewModel.deleteData(data)
            }
        }
        dialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData()
    }
}