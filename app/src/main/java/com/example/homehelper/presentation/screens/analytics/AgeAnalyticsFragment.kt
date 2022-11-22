package com.example.homehelper.presentation.screens.analytics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentAddEventBinding
import com.example.homehelper.databinding.FragmentAgeAnalyticsBinding
import org.eazegraph.lib.models.PieModel

class AgeAnalyticsFragment : Fragment() {

    private var _binding: FragmentAgeAnalyticsBinding? = null
    private val binding: FragmentAgeAnalyticsBinding
        get() = _binding ?: throw RuntimeException("FragmentAgeAnalyticsBinding = null!")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAgeAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pieChart.addPieSlice(PieModel(12F,R.color.analytics_age_first))
        binding.pieChart.addPieSlice(PieModel(45F,R.color.analytics_age_second))
        binding.pieChart.addPieSlice(PieModel(18F,R.color.analytics_age_third))
        binding.pieChart.startAnimation()
    }

    companion object {

        fun newInstance() =
            AgeAnalyticsFragment()
    }
}