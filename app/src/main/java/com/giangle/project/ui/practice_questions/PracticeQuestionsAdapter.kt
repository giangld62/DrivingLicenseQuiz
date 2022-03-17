package com.giangle.project.ui.practice_questions

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.giangle.project.databinding.ItemQuestionBinding
import com.giangle.project.db.entity.Question
import com.giangle.project.util.Const.B1

class PracticeQuestionsAdapter(private var typeOfContest: String) :
    RecyclerView.Adapter<PracticeQuestionsAdapter.ViewHolder>() {

    private val questions = arrayListOf<Question>()
    private var page = 0

    fun submitList(temps: List<Question>,page: Int){
        val diff = QuestionsDiff(questions,temps)
        val diffResult = DiffUtil.calculateDiff(diff)
        questions.clear()
        questions.addAll(temps)
        this.page = page
        diffResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemQuestionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount() = questions.size

    inner class ViewHolder(private val binding: ItemQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(question: Question){
            binding.data = question
            binding.llAnswerD.visibility = View.VISIBLE
            binding.llAnswerC.visibility = View.VISIBLE
            binding.ivQuestionImage.visibility = View.VISIBLE
            when(typeOfContest){
                B1 -> {
                    if(question.cauDiemLiet == 1){
                        binding.tvQuestionNumber.text = "Câu ${page * 30 + adapterPosition + 1} (câu điểm liệt)"
                        binding.tvQuestionNumber.setTextColor(Color.parseColor("#D50000"))
                    }
                    else{
                        binding.tvQuestionNumber.text = "Câu ${page * 30 + adapterPosition + 1}"
                        binding.tvQuestionNumber.setTextColor(Color.parseColor("#3700B3"))
                    }
                }
                else -> {
                    if(question.cauDiemLiet == 1){
                        binding.tvQuestionNumber.text = "Câu ${page * 25 + adapterPosition + 1} (câu điểm liệt)"
                        binding.tvQuestionNumber.setTextColor(Color.parseColor("#D50000"))
                    }
                    else{
                        binding.tvQuestionNumber.text = "Câu ${page * 25 + adapterPosition + 1}"
                        binding.tvQuestionNumber.setTextColor(Color.parseColor("#3700B3"))
                    }
                }
            }
            if (question.c == null)
                binding.llAnswerC.visibility = View.GONE
            else
                binding.tvAnswerC.text = question.c
            if (question.d == null)
                binding.llAnswerD.visibility = View.GONE
            else
                binding.tvAnswerD.text = question.d
        }
    }
}