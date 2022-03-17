package com.giangle.project.ui.tips.practice_tips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.giangle.project.R
import com.giangle.project.databinding.FragmentPracticeB1b2Binding

class PracticeB1B2Fragment : Fragment() {
    private lateinit var binding: FragmentPracticeB1b2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPracticeB1b2Binding.inflate(inflater,container,false)
        binding.btnB1b2Experience.setOnClickListener {
            findNavController().navigate(R.id.action_global_b1B2ExamExperienceFragment)
        }
        return binding.root
    }
}