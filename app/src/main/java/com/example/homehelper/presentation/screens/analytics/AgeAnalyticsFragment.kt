package com.example.homehelper.presentation.screens.analytics

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentAddEventBinding
import com.example.homehelper.databinding.FragmentAgeAnalyticsBinding
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.adapters.chats.AdapterUsersMes
import com.example.homehelper.presentation.viewmodels.ChatsListViewModel
import com.example.homehelper.presentation.viewmodels.UsersListViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import javax.inject.Inject

class AgeAnalyticsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val usersListViewModel: UsersListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UsersListViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentAgeAnalyticsBinding? = null
    private val binding: FragmentAgeAnalyticsBinding
        get() = _binding ?: throw RuntimeException("FragmentAgeAnalyticsBinding = null!")


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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

    }

    private fun setupPieChart(first: Float, second: Float, third: Float) {
        binding.pieChart.addPieSlice(PieModel("first",
            first,
            ContextCompat.getColor(requireContext(), R.color.analytics_age_first)))
        binding.pieChart.addPieSlice(PieModel("second",
            second,
            ContextCompat.getColor(requireContext(), R.color.analytics_age_second)))
        binding.pieChart.addPieSlice(PieModel("third",
            third,
            ContextCompat.getColor(requireContext(), R.color.analytics_age_third)))
        binding.pieChart.startAnimation()
    }

    companion object {

        fun newInstance() =
            AgeAnalyticsFragment()
    }
}