package com.giangle.project.ui.tips.practice_tips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.giangle.project.databinding.FragmentA1a2ExamExperienceBinding

class A1A2ExamExperienceFragment : Fragment() {
    private lateinit var binding: FragmentA1a2ExamExperienceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentA1a2ExamExperienceBinding.inflate(inflater,container,false)
        return binding.root
    }
}