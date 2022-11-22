package com.example.homehelper.presentation.screens.analytics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentAgeAnalyticsBinding
import com.example.homehelper.domain.entities.User
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.viewmodels.UsersListViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
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
        usersListViewModel.getAllUsers().observe(viewLifecycleOwner) { users ->
            countAge(users)
        }
    }

    private fun countAge(users: List<User>) {
        val validUsers = users.filter { it.age != 0 }
        val firstGroupCount = validUsers.filter { it.age in 18..23 }.size.toFloat()
        val secondGroupCount = validUsers.filter { it.age in 24..34 }.size.toFloat()
        val thirdGroupCount = validUsers.filter { it.age in 35..54 }.size.toFloat()
        val averageAge = validUsers.map { it.age }.average()
        binding.tvAverageAge.text = averageAge.toInt().toString()
        binding.tvAgeFirstCount.text = firstGroupCount.toInt().toString()
        binding.tvAgeSecondCount.text = secondGroupCount.toInt().toString()
        binding.tvAgeThirdCount.text = thirdGroupCount.toInt().toString()
        setupPieChart(firstGroupCount, secondGroupCount, thirdGroupCount)
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