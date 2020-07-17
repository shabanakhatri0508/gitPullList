package com.githubopenpulls

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.githubopenpulls.databinding.RawPullListItemBinding
import com.githubopenpulls.models.PullInfo

class PullListAdapter : RecyclerView.Adapter<PullListAdapter.ViewHolder>() {
    private var pullList: ArrayList<PullInfo> = arrayListOf()

    class ViewHolder(private val itemBinding: RawPullListItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: PullInfo) {
            itemBinding.data = data
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding: RawPullListItemBinding =
            RawPullListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return pullList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pullList[position]
        holder.bind(item)
    }

    fun updateList(list: ArrayList<PullInfo>) {
        this.pullList = list
    }
}