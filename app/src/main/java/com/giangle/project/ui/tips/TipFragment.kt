package com.giangle.project.ui.tips

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.giangle.project.R
import com.giangle.project.databinding.FragmentTipBinding
import com.giangle.project.ui.tips.practice_tips.PracticeFragment
import com.giangle.project.ui.tips.theory_tips.TheoryFragment

class TipFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentTipBinding
    private var mCurrentFragmentTag = "Theory"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTipBinding.inflate(inflater, container, false)
        changeFragment()
        binding.btnPractice.setOnClickListener(this)
        binding.btnTheory.setOnClickListener(this)
        return binding.root
    }

    private fun changeFragment() {
        when(mCurrentFragmentTag){
            "Theory" ->{
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                    .replace(R.id.fl_container, TheoryFragment(), mCurrentFragmentTag).commit()
            }
            "Practice" ->{
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                    .replace(R.id.fl_container, PracticeFragment(), mCurrentFragmentTag).commit()
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_theory -> {
                mCurrentFragmentTag = "Theory"
                binding.btnTheory.setBackgroundColor(Color.parseColor("#FDC313"))
                binding.btnPractice.setBackgroundColor(Color.parseColor("#FFAAAAAA"))
            }
            R.id.btn_practice -> {
                mCurrentFragmentTag = "Practice"
                binding.btnPractice.setBackgroundColor(Color.parseColor("#FDC313"))
                binding.btnTheory.setBackgroundColor(Color.parseColor("#FFAAAAAA"))
            }
        }
        changeFragment()
    }
}