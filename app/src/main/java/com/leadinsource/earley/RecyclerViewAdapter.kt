package com.leadinsource.earley

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val callback: Callback) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val data: MutableList<Project> = mutableListOf()

    fun setData(newData: List<Project>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        if(viewType==ViewType.CURRENT_ITEM.ordinal)
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.current_list_item, parent, false))

        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        if (position==0) return ViewType.CURRENT_ITEM.ordinal

        return ViewType.NORMAL_ITEM.ordinal
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView?.text = data[position].name
        holder.checkBox?.setOnClickListener {
            holder.checkBox.isChecked = false
            callback.onClick(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView? = view.findViewById(R.id.textView)
        val checkBox: CheckBox? = view.findViewById(R.id.checkBox)

    }

    interface Callback {
        fun onClick(project: Project)
    }

}

private enum class ViewType {
    CURRENT_ITEM,
    NORMAL_ITEM
}
