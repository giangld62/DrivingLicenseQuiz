package com.giangle.project.ui.tips.practice_tips

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.giangle.project.R
import com.giangle.project.databinding.FragmentPracticeBinding
import com.giangle.project.util.Const


class PracticeFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentPracticeBinding
    private var mCurrentFragmentTag = Const.TAG_A1_A2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPracticeBinding.inflate(inflater,container,false)
        changeFragment()
        binding.btnPracticeA1a2.setOnClickListener(this)
        binding.btnPracticeB1b2.setOnClickListener(this)
        return binding.root
    }

    private fun changeFragment() {
        when(mCurrentFragmentTag){
            Const.TAG_A1_A2 ->{
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right)
                    .replace(R.id.fl_container2, PracticeA1A2Fragment(), mCurrentFragmentTag).commit()
            }
            Const.TAG_B1_B2 ->{
                requireActivity().supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left)
                    .replace(R.id.fl_container2, PracticeB1B2Fragment(), mCurrentFragmentTag).commit()
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_practice_a1a2 ->{
                mCurrentFragmentTag = Const.TAG_A1_A2
                binding.btnPracticeA1a2.setBackgroundColor(Color.parseColor("#FDC313"))
                binding.btnPracticeB1b2.setBackgroundColor(Color.parseColor("#FFAAAAAA"))
            }

            R.id.btn_practice_b1b2 ->{
                mCurrentFragmentTag = Const.TAG_B1_B2
                binding.btnPracticeB1b2.setBackgroundColor(Color.parseColor("#FDC313"))
                binding.btnPracticeA1a2.setBackgroundColor(Color.parseColor("#FFAAAAAA"))
            }
        }
        changeFragment()
    }

}