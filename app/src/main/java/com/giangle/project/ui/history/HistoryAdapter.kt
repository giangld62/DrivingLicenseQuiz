package com.giangle.project.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.giangle.project.databinding.ItemContestHistoryBinding
import com.giangle.project.model.ContestState

val diffTask = object : DiffUtil.ItemCallback<ContestState>() {
    override fun areItemsTheSame(oldItem: ContestState, newItem: ContestState): Boolean {
        return oldItem.idType == newItem.idType
    }

    override fun areContentsTheSame(oldItem: ContestState, newItem: ContestState): Boolean {
        return oldItem == newItem
    }
}

class HistoryAdapter(private val inter: IHistoryAdapter) : ListAdapter<ContestState, HistoryAdapter.ViewHolder>(diffTask) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemContestHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemContestHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contestState: ContestState){
            binding.data = contestState
            binding.root.setOnClickListener{
                inter.onItemClick(adapterPosition)
            }
            binding.executePendingBindings()
        }
    }

    interface IHistoryAdapter {
        fun onItemClick(position: Int)
    }
}