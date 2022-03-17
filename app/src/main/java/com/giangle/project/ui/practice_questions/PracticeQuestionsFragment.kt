package com.giangle.project.ui.practice_questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.giangle.project.R
import com.giangle.project.databinding.FragmentPracticeQuestionsBinding
import com.giangle.project.db.entity.Question
import com.giangle.project.ui.main.MainViewModel
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PracticeQuestionsFragment : Fragment() {

    private var maxPage = 0
    private var pageNumber = 1
    private lateinit var binding: FragmentPracticeQuestionsBinding
    private lateinit var adapter: PracticeQuestionsAdapter
    private val args by navArgs<PracticeQuestionsFragmentArgs>()
    private val viewModel by viewModels<PracticeQuestionsViewModel> {
        ViewModelFactory(requireActivity().application)
    }
    private var listOfQuestionList = listOf<List<Question>>()
    private val mainViewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPracticeQuestionsBinding.inflate(inflater, container, false)
        setUpData()
        setUpAction()
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mainViewModel.updateTextView("")
                    findNavController().navigateUp()
                }
            })
        return binding.root
    }

    private fun setUpAction() {
        binding.btnNext.setOnClickListener {
            updatePagerNumber(true)
        }
        binding.btnPrevious.setOnClickListener {
            updatePagerNumber(false)
        }
    }

    private fun setUpData() {
        binding.pbQuestion.visibility = View.VISIBLE
        adapter = PracticeQuestionsAdapter(args.typeOfContest)
        maxPage = when (args.typeOfContest) {
            A1 -> 8
            A2 -> 18
            else -> 20
        }
        requireActivity().lifecycleScope.launch(Dispatchers.IO) {
            listOfQuestionList = viewModel.getListOfQuestionList(args.typeOfContest)
            withContext(Dispatchers.Main) {
                adapter.submitList(listOfQuestionList[0], 0)
                binding.pbQuestion.visibility = View.GONE
            }
        }
        binding.rcvQuestion.adapter = adapter
        binding.rcvQuestion.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updatePagerNumber(nextPage: Boolean) {
        if (nextPage) {
            if (pageNumber < maxPage) {
                pageNumber++
                adapter.submitList(listOfQuestionList[pageNumber - 1], pageNumber - 1)
                binding.rcvQuestion.scrollToPosition(0)
            }
        } else {
            if (pageNumber > 1) {
                pageNumber--
                adapter.submitList(listOfQuestionList[pageNumber - 1], pageNumber - 1)
                binding.rcvQuestion.scrollToPosition(0)
            }
        }
        mainViewModel.updateTextView("$pageNumber/$maxPage")
    }

}