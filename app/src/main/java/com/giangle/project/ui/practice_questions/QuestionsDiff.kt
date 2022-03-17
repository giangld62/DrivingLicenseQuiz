package com.giangle.project.ui.practice_questions

import androidx.recyclerview.widget.DiffUtil
import com.giangle.project.db.entity.Question

class QuestionsDiff(
    private val oldQuestions: List<Question>,
    private val newQuestions: List<Question>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldQuestions.size

    override fun getNewListSize() = newQuestions.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldQuestions[oldItemPosition].id == newQuestions[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldQuestions[oldItemPosition] == newQuestions[newItemPosition]
    }
}