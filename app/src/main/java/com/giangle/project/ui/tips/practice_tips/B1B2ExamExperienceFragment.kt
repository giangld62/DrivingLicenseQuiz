package com.giangle.project.ui.tips.practice_tips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.giangle.project.databinding.FragmentB1b2ExamExperienceBinding

class B1B2ExamExperienceFragment : Fragment() {
    private lateinit var binding: FragmentB1b2ExamExperienceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentB1b2ExamExperienceBinding.inflate(inflater,container,false)
        return binding.root
    }
}