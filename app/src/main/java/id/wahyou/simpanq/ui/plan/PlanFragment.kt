package id.wahyou.simpanq.ui.plan

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import id.wahyou.simpanq.R
import id.wahyou.simpanq.data.model.Planning
import id.wahyou.simpanq.databinding.BottomsheetLinkBinding
import id.wahyou.simpanq.databinding.BottomsheetPlanBinding
import id.wahyou.simpanq.databinding.CustomDialogBinding
import id.wahyou.simpanq.databinding.FragmentPlanBinding
import id.wahyou.simpanq.ui.plan.add.AddPlanActivity
import id.wahyou.simpanq.ui.plan.edit.EditPlanActivity
import id.wahyou.simpanq.utils.Utils

@AndroidEntryPoint
class PlanFragment : Fragment() {

    private val binding : FragmentPlanBinding by lazy {
        FragmentPlanBinding.inflate(layoutInflater)
    }

    private val adapter : PlanAdapter by lazy {
        PlanAdapter{item -> showDetail(item)}
    }

    private val bottomSheetDialog : BottomSheetDialog by lazy {
        BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme)
    }

    private val viewModel : PlanViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupViewModel()
        setupListener()
    }

    private fun setupAdapter(){
        with(binding){
            rvPlan.also {
                it.adapter = adapter
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
            }
        }
    }

    private fun setupViewModel(){
        viewModel.getState.observe(viewLifecycleOwner, { plan ->
            if (plan.isEmpty()) {
                showComponent(true)
            } else {
                adapter.setData(plan)
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

    private fun showComponent(isShow: Boolean){
        with(binding) {
            if(isShow) {
                imgBackground.visibility = View.VISIBLE
                btnAdd.visibility = View.VISIBLE
                rvPlan.visibility = View.GONE
                fabAdd.visibility = View.GONE
                etSearch.visibility = View.GONE
            }else {
                imgBackground.visibility = View.GONE
                btnAdd.visibility = View.GONE
                rvPlan.visibility = View.VISIBLE
                fabAdd.visibility = View.VISIBLE
                etSearch.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetail(data: Planning) {
        val binding = BottomsheetPlanBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        with(binding) {

            tvTitle.text = data.title
            tvLocation.text = data.location
            tvDate.text = Utils.dateFormat(
                    data.date,
                    "dd-mm-yyyy",
                    "dd MMMM yyyy"
            )
            tvTime.text = "${data.startTime} - ${data.endTime}"
            tvNotes.text = data.notes

            btnClose.setOnClickListener { bottomSheetDialog.dismiss() }
            btnEdit.setOnClickListener {
                bottomSheetDialog.dismiss()
                startActivity(Intent(requireContext(), EditPlanActivity::class.java).also {
                    it.putExtra("data", data)
                })
            }
            btnDelete.setOnClickListener { deletePlan(data) }
        }
        bottomSheetDialog.show()
    }

    private fun deletePlan(data: Planning){
        val binding = CustomDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false)
        dialog.setContentView(binding.root)

        with(binding){
            tvTitle.text = getString(R.string.title_dialog_delete_plan)
            tvBody.text = getString(R.string.title_dialog_body_plan)
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

    private fun setupListener(){
        with(binding) {
            btnAdd.setOnClickListener {
                startActivity(Intent(requireContext(), AddPlanActivity::class.java))
            }
            fabAdd.setOnClickListener {
                startActivity(Intent(requireContext(), AddPlanActivity::class.java))
            }
        }
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