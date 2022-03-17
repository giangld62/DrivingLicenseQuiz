package com.giangle.project.ui.traffic_signs

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giangle.project.databinding.ItemTrafficSignBinding
import com.giangle.project.db.entity.TrafficSign

class TrafficSignAdapter(private val inter: ITrafficSign) : RecyclerView.Adapter<TrafficSignAdapter.ViewHolder>() {
    private val trafficSigns = arrayListOf<TrafficSign>()

    fun submitList(temps: List<TrafficSign>) {
        trafficSigns.clear()
        trafficSigns.addAll(temps)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTrafficSignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trafficSigns[position])
    }

    override fun getItemCount() = trafficSigns.size

    inner class ViewHolder(private val binding: ItemTrafficSignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trafficSign: TrafficSign) {
            binding.data = trafficSign
            when (trafficSign.loaiBien) {
                1 -> {
                    inter.setTitleType("Biển báo nguy hiểm")
                    binding.content.setTextColor(Color.parseColor("#000000"))
                }
                2 -> {
                    inter.setTitleType("Biển báo cấm")
                    binding.content.setTextColor(Color.parseColor("#F013A4"))
                }
                3 -> {
                    inter.setTitleType("Biển báo hiệu lệnh")
                    binding.content.setTextColor(Color.parseColor("#000000"))
                }
                4 -> {
                    inter.setTitleType("Biển báo chỉ dẫn")
                    binding.content.setTextColor(Color.parseColor("#F013A4"))
                }
                5 -> {
                    inter.setTitleType("Biển báo phụ")
                    binding.content.setTextColor(Color.parseColor("#000000"))

                }
                else -> {
                    inter.setTitleType("Bien bao")
                    binding.content.setTextColor(Color.parseColor("#000000"))
                }
            }
        }
    }

    interface ITrafficSign{
        fun setTitleType(type: String)
    }
}