package com.giangle.project.ui.do_contest

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.giangle.project.databinding.ItemExamQuestionsBinding
import com.giangle.project.db.entity.Question
import com.giangle.project.ui.practice_questions.QuestionsDiff
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.Const.B1
import com.giangle.project.util.Const.B2
import com.giangle.project.util.Const.CAU_HOI_DIEM_LIET
import com.giangle.project.util.Const.RANDOM_QUESTION
import com.giangle.project.util.MapperService

class DoContestAdapter(typeOfContest: String,position: Int) :
    RecyclerView.Adapter<DoContestAdapter.ViewHolder>() {
    private var questions = arrayListOf<Question>()
    private var questionNumber = 1
    var isSubmitted = false

    var yourAnswer: Array<BooleanArray> = Array(
        when (position) {
            -1 ->{
                when(typeOfContest){
                    A1 -> 20
                    A2 -> 50
                    else -> 60
                }
            }
            else ->{
                when(typeOfContest){
                    B2 -> 35
                    B1 -> 30
                    else -> 25
                }
            }
        }
    ) {
        BooleanArray(4)
    }

    fun submitList(temps: List<Question>, questionNumber: Int) {
        val diff = QuestionsDiff(questions, temps)
        val diffResult = DiffUtil.calculateDiff(diff)
        questions.clear()
        questions.addAll(temps)
        this.questionNumber = questionNumber
        this.isSubmitted = isSubmitted
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemExamQuestionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount() = questions.size

    inner class ViewHolder(private val binding: ItemExamQuestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(question: Question) {
            setIsRecyclable(false)
            binding.rbC.visibility = View.VISIBLE
            binding.rbD.visibility = View.VISIBLE
            binding.ivQuestionImage.visibility = View.VISIBLE
            binding.rlAnswer.visibility =
                if (isSubmitted) View.VISIBLE else View.INVISIBLE

            binding.rbA.isEnabled = !isSubmitted
            binding.rbB.isEnabled = !isSubmitted
            binding.rbC.isEnabled = !isSubmitted
            binding.rbD.isEnabled = !isSubmitted

            binding.rbA.isChecked = yourAnswer[questionNumber - 1][0]
            binding.rbB.isChecked = yourAnswer[questionNumber - 1][1]
            binding.rbC.isChecked = yourAnswer[questionNumber - 1][2]
            binding.rbD.isChecked = yourAnswer[questionNumber - 1][3]

            binding.rbA.setOnCheckedChangeListener { buttonView, isChecked ->
                yourAnswer[questionNumber - 1][0] = isChecked
            }
            binding.rbB.setOnCheckedChangeListener { buttonView, isChecked ->
                yourAnswer[questionNumber - 1][1] = isChecked
            }
            binding.rbC.setOnCheckedChangeListener { buttonView, isChecked ->
                yourAnswer[questionNumber - 1][2] = isChecked
            }
            binding.rbD.setOnCheckedChangeListener { buttonView, isChecked ->
                yourAnswer[questionNumber - 1][3] = isChecked
            }

            if (question.cauDiemLiet == 1) {
                binding.tvQuestionNumber.text = "Câu $questionNumber (câu điểm liệt)"
                binding.tvQuestionNumber.setTextColor(Color.parseColor("#D50000"))
            } else {
                binding.tvQuestionNumber.text = "Câu $questionNumber"
                binding.tvQuestionNumber.setTextColor(Color.parseColor("#3700B3"))
            }
            binding.tvQuestion.text = question.cauHoi
            binding.rbA.text = "1 - ${question.a}"
            binding.rbB.text = "2 - ${question.b}"
            if (question.c.isNullOrEmpty())
                binding.rbC.visibility = View.GONE
            else
                binding.rbC.text = "3 - ${question.c}"
            if (question.d.isNullOrEmpty())
                binding.rbD.visibility = View.GONE
            else
                binding.rbD.text = "4 - ${question.d}"
            if (question.anh == 0)
                binding.ivQuestionImage.visibility = View.GONE
            else
                binding.ivQuestionImage.setImageBitmap(question.bienBao)
            binding.tvRightAnswer.text = "Đáp án: " + question.dapAnDung
        }
    }

    fun setAnswerHistory(answers: MutableList<String>) {
        yourAnswer = MapperService.convertListStringToBooleanMatrix(answers)
    }
}