package com.giangle.project.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.giangle.project.R
import com.giangle.project.databinding.FragmentDetailContestBinding
import com.giangle.project.db.entity.Question
import com.giangle.project.ui.do_contest.DoContestAdapter
import com.giangle.project.util.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetailContestFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentDetailContestBinding
    private val viewModel by viewModels<HistoryViewModel>{
        ViewModelFactory(requireActivity().application)
    }
    private var questions = arrayListOf<Question>()
    private val args by navArgs<DetailContestFragmentArgs>()
    private lateinit var adapter: DoContestAdapter
    private var questionNumber = 1
    private var maxQuestion = 0
    private var answers = mutableListOf<String>()
    private var currentQuestion = arrayListOf<Question>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailContestBinding.inflate(inflater,container,false)
        setUpAction()
        setUpData()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setUpData() {
        adapter = DoContestAdapter(args.typeOfContest,0)
        adapter.isSubmitted = true
        requireActivity().lifecycleScope.launch(Dispatchers.IO){
            questions.clear()
            questions.addAll(viewModel.getQuestionListOfContest(args.idType))
            maxQuestion = questions.size
            currentQuestion.add(questions[0])
            for(question in questions){
                answers.add(question.cauTraLoi!!)
            }
            withContext(Dispatchers.Main){
                adapter.submitList(currentQuestion,1)
                adapter.setAnswerHistory(answers)
                binding.tvCurrentQuestion.text = "Câu $questionNumber/$maxQuestion"
            }
        }
        binding.rcvQuestion.adapter = adapter
        binding.rcvQuestion.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setUpAction() {
        binding.btnNext.setOnClickListener(this)
        binding.btnPrevious.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_previous ->{
                toNextQuestion(false)
            }

            R.id.btn_next ->{
                toNextQuestion(true)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun toNextQuestion(isNext: Boolean) {
        if (isNext) {
            if (questionNumber < maxQuestion) {
                questionNumber += 1
                currentQuestion.clear()
                currentQuestion.add(questions[questionNumber - 1])
                adapter.submitList(currentQuestion, questionNumber)
                adapter.notifyDataSetChanged()
            }
        } else {
            if (questionNumber > 1) {
                questionNumber -= 1
                currentQuestion.clear()
                currentQuestion.add(questions[questionNumber - 1])
                adapter.submitList(currentQuestion, questionNumber)
                adapter.notifyDataSetChanged()
            }
        }
        binding.tvCurrentQuestion.text = "Câu $questionNumber/$maxQuestion"
    }

}