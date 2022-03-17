package com.giangle.project.ui.choose_contest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.giangle.project.R
import com.giangle.project.databinding.ItemContestBinding
import com.giangle.project.model.ContestStatus
import com.giangle.project.util.Const.FAILED
import com.giangle.project.util.Const.PASSED
import com.giangle.project.util.Const.UNTESTED

val diffTask = object : DiffUtil.ItemCallback<ContestStatus>() {
    override fun areItemsTheSame(oldItem: ContestStatus, newItem: ContestStatus): Boolean {
        return oldItem.idType == newItem.idType
    }

    override fun areContentsTheSame(oldItem: ContestStatus, newItem: ContestStatus): Boolean {
        return oldItem == newItem
    }
}

class ChooseContestAdapter(private val inter: IChooseContest) :
    ListAdapter<ContestStatus, ChooseContestAdapter.ViewHolder>(diffTask) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemContestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemContestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(contestStatus: ContestStatus) {
            binding.tvExamNumber.text = "${adapterPosition + 1}"
            binding.root.setOnClickListener {
                inter.onItemClick(adapterPosition)
            }
            when (contestStatus.status) {
                UNTESTED ->
                    binding.ivState.setImageResource(R.drawable.untest_state)
                PASSED ->
                    binding.ivState.setImageResource(R.drawable.pass_state)
                FAILED ->
                    binding.ivState.setImageResource(R.drawable.fail_state)
            }
        }
    }

    interface IChooseContest {
        fun onItemClick(position: Int)
    }
}