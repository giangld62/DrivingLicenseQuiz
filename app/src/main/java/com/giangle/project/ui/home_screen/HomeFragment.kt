package com.giangle.project.ui.home_screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.giangle.project.R
import com.giangle.project.databinding.FragmentHomeBinding
import com.giangle.project.databinding.OptionDialog2Binding
import com.giangle.project.databinding.OptionDialogBinding
import com.giangle.project.model.Photo
import com.giangle.project.ui.custom_ui.OptionDialog
import com.giangle.project.ui.main.MainViewModel
import com.giangle.project.ui.practice_questions.PracticeQuestionsFragment
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.Const.B1
import com.giangle.project.util.Const.B2
import com.giangle.project.util.Const.LEARNING_THEORY
import com.giangle.project.util.Const.TAKE_A_EXAM
import com.giangle.project.util.ViewModelFactory

class HomeFragment : Fragment(), View.OnClickListener, OptionDialog.IOptionDialog {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<ImageSliderViewModel>()
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private val viewModel2 by activityViewModels<MainViewModel> {
        ViewModelFactory(null)
    }
    private var checkButtonTapped = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpViewPager()
        addEvents()
        return binding.root
    }

    private fun addEvents() {
        binding.imbTrafficSigns.setOnClickListener(this)
        binding.imbTips.setOnClickListener(this)
        binding.imbMakeQuiz.setOnClickListener(this)
        binding.imbQuestions.setOnClickListener(this)
    }

    private fun setUpViewPager() {
        val photoList = arrayListOf<Photo>()
        val viewPager2Adapter = ViewPager2Adapter()
        binding.pagerTitle.adapter = viewPager2Adapter
        viewModel.images.observe(this, {
            viewPager2Adapter.submitList(it)
            photoList.addAll(it)
            binding.indicator.setViewPager(binding.pagerTitle)
        })
        mHandler = Handler(Looper.getMainLooper())
        mRunnable = Runnable {
            if (binding.pagerTitle.currentItem == photoList.size - 1)
                binding.pagerTitle.currentItem = 0
            else
                binding.pagerTitle.currentItem += 1
        }
        binding.pagerTitle.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mHandler.removeCallbacks(mRunnable)
                mHandler.postDelayed(mRunnable, 4000)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(mRunnable)
    }

    override fun onResume() {
        super.onResume()
        mHandler.postDelayed(mRunnable, 4000)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imb_traffic_signs -> {
                val action = HomeFragmentDirections.actionHomeFragmentToTrafficSignFragment()
                findNavController().navigate(action)
            }

            R.id.imb_tips -> {
                val action = HomeFragmentDirections.actionHomeFragmentToTipFragment()
                findNavController().navigate(action)
            }

            R.id.imb_questions -> {
                checkButtonTapped = 1
                OptionDialog(
                    OptionDialogBinding.inflate(layoutInflater, binding.llRoot, false),
                    LEARNING_THEORY, this
                ).show(requireActivity().supportFragmentManager, "OptionDialog")
            }

            R.id.imb_make_quiz ->{
                checkButtonTapped = 2
                OptionDialog(
                    OptionDialog2Binding.inflate(layoutInflater, binding.llRoot, false),
                    TAKE_A_EXAM, this
                ).show(requireActivity().supportFragmentManager, "OptionDialog")
            }
        }
    }

    override fun sendTypeContest(type: String) {
        var fragmentLabel = ""
        if (checkButtonTapped == 1) {
            when(type){
                A1 ->{
                    fragmentLabel = "200 câu lý thuyết A1"
                    viewModel2.updateTextView("1/8")
                }
                A2 ->{
                    fragmentLabel = "450 câu lý thuyết A2"
                    viewModel2.updateTextView("1/18")
                }
                B1 ->{
                    fragmentLabel = "600 câu lý thuyết B1,B2"
                    viewModel2.updateTextView("1/20")
                }
            }
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPracticeQuestionsFragment(
                    type,fragmentLabel
                )
            )
        }
        else if(checkButtonTapped == 2){
            fragmentLabel = when(type){
                A1 -> "Làm đề thi A1"
                A2 -> "Làm đề thi A2"
                B1 -> "Làm đề thi B1"
                else -> "Làm đề thi B2"
            }
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToChooseContestFragment(
                    type,fragmentLabel
                )
            )
        }
    }
}