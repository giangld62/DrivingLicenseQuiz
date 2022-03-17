package com.giangle.project.ui.tips.practice_tips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.giangle.project.R
import com.giangle.project.databinding.FragmentPracticeA1a2Binding

class PracticeA1A2Fragment : Fragment() {
    private lateinit var binding: FragmentPracticeA1a2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPracticeA1a2Binding.inflate(inflater, container, false)
        binding.btnA1a2Experience.setOnClickListener {
            findNavController().navigate(R.id.action_global_a1A2ExamExperienceFragment)
        }
        return binding.root
    }

}