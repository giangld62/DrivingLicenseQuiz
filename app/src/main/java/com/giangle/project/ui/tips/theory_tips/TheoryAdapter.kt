package com.giangle.project.ui.tips.theory_tips

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giangle.project.databinding.ItemTipBinding
import com.giangle.project.db.entity.Tip

class TheoryAdapter(private val inter: ITheoryAdapter) : RecyclerView.Adapter<TheoryAdapter.ViewHolder>() {
    private val tips = arrayListOf<Tip>()

    fun submitList(temps: List<Tip>){
        tips.clear()
        tips.addAll(temps)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTipBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tips[position])
    }

    override fun getItemCount() = tips.size

    inner class ViewHolder(private val binding: ItemTipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tip: Tip){
            binding.data = tip
            when (tip.loaiMeo) {
                0 -> {
                    inter.setTitleType("MẸO CÂU HỎI LÝ THUYẾT")
                    binding.tvTipContent.setTextColor(Color.BLACK)
                }
                1 -> {
                    inter.setTitleType("MẸO CÂU HỎI BIỂN BÁO")
                    binding.tvTipContent.setTextColor(Color.BLUE)
                }
                2 -> {
                    inter.setTitleType("MẸO CÂU HỎI SA HÌNH")
                    binding.tvTipContent.setTextColor(Color.BLACK)
                }
                else -> {
                    inter.setTitleType("Bien bao")
                    binding.tvTipContent.setTextColor(Color.BLACK)
                }
            }
        }
    }

    interface ITheoryAdapter{
        fun setTitleType(type: String)
    }

}