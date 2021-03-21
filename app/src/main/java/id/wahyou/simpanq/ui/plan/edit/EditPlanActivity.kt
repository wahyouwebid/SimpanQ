package id.wahyou.simpanq.ui.plan.edit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import id.wahyou.simpanq.R
import id.wahyou.simpanq.data.model.Planning
import id.wahyou.simpanq.databinding.ActivityAddPlanBinding
import id.wahyou.simpanq.utils.Utils
import java.util.*

@AndroidEntryPoint
class EditPlanActivity : AppCompatActivity() {
    private val binding : ActivityAddPlanBinding by lazy {
        ActivityAddPlanBinding.inflate(layoutInflater)
    }

    private val viewModel : EditPlanViewModel by viewModels()

    private var date : String = ""

    private val data : Planning? by lazy {
        intent.getParcelableExtra("data")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getData()
        setupViewModel()
        setupListener()
    }

    private fun getData() {
        with(binding){
            etTitle.setText(data?.title)
            etLocation.setText(data?.location)
            etDate.setText(data?.date?.let {
                Utils.dateFormat(
                    it,
                    "dd-mm-yyyy",
                    "dd MMMM yyyy"
                )
            })
            etStartTime.setText(data?.startTime)
            etEndTime.setText(data?.endTime)
            etNotes.setText(data?.notes)
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
                if (
                    etTitle.text.isNotEmpty() &&
                    etDate.text.isNotEmpty() &&
                    etStartTime.text.isNotEmpty() &&
                    etLocation.text.isNotEmpty()
                ) {
                    saveData()
                } else {
                    showMessage(getString(R.string.title_is_not_empty))
                }
            }
            imgBack.setOnClickListener { finish() }
            etDate.setOnClickListener { setupDate() }
            etStartTime.setOnClickListener { setupTime(etStartTime) }
            etEndTime.setOnClickListener {
                if(etStartTime.text.isNotEmpty()){
                    setupTime(etEndTime)
                }else {
                    showMessage(getString(R.string.title_plan_warning))
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupDate(){
        with(binding){
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this@EditPlanActivity, { _, y, m, d ->
                date = "$d-$m-$y"

                etDate.setText(
                    Utils.dateFormat(
                    date,
                    "dd-mm-yyyy",
                    "dd MMMM yyyy"
                    )
                )
            }, year, month, day)

            datePicker.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupTime(editText: EditText){
        val calendar = Calendar.getInstance()
        val hh = calendar.get(Calendar.HOUR_OF_DAY)
        val mm = calendar.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(this@EditPlanActivity, { _, hour, minute ->
            editText.setText("$hour:$minute")
        } , hh, mm,true)

        timePicker.show()
    }

    private fun saveData() {
        with(binding) {
            val date = if(date.isEmpty()) data?.date else date

            viewModel.updatePlan(
                Planning(
                    data!!.id,
                    etTitle.text.toString(),
                    etLocation.text.toString(),
                    date!!,
                    etStartTime.text.toString(),
                    etEndTime.text.toString(),
                    etNotes.text.toString()
                )
            )
        }
    }

    private fun showMessage(message: String){
        Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show()
    }
}