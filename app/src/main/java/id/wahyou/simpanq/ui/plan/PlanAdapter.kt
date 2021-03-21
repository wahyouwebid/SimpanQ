package id.wahyou.simpanq.ui.plan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.wahyou.simpanq.data.model.Planning
import id.wahyou.simpanq.databinding.AdapterPlanBinding
import id.wahyou.simpanq.utils.Utils

class PlanAdapter (
    private val showDetail: (Planning) -> Unit
) : RecyclerView.Adapter<PlanAdapter.ViewHolder>() {

    private var data = ArrayList<Planning>()

    fun setData(link: List<Planning>) {
        data.clear()
        data.addAll(link)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]

            tvTitle.text = item.title
            tvDate.text = Utils.dateFormat(
                    item.date,
                    "dd-mm-yyyy",
                    "dd MMMM yyyy"
            )
            tvTime.text = "${item.startTime} - ${item.endTime}"

            root.setOnClickListener {
                showDetail(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterPlanBinding) : RecyclerView.ViewHolder(view.root)

}