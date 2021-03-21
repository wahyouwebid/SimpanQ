package id.wahyou.simpanq.ui.link

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.databinding.AdapterLinkBinding

class LinkAdapter (
    private val showDetail: (Link) -> Unit
) : RecyclerView.Adapter<LinkAdapter.ViewHolder>() {

    private var data = ArrayList<Link>()

    fun setData(link: List<Link>) {
        data.clear()
        data.addAll(link)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            val item = data[position]

            if(item.title.isEmpty()){
                tvName.text = item.content
            } else {
                tvName.text = item.title
            }

            root.setOnClickListener {
                showDetail(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterLinkBinding) : RecyclerView.ViewHolder(view.root)

}