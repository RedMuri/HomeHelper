package com.example.homehelper.presentation.screens.analytics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentAgeAnalyticsBinding
import com.example.homehelper.databinding.FragmentGenderAnalyticsBinding
import com.example.homehelper.domain.entities.User
import com.example.homehelper.presentation.HomeHelperApp
import com.example.homehelper.presentation.viewmodels.UsersListViewModel
import com.example.homehelper.presentation.viewmodels.ViewModelFactory
import org.eazegraph.lib.models.PieModel
import javax.inject.Inject

class GenderAnalyticsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val usersListViewModel: UsersListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UsersListViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as HomeHelperApp).component
    }

    private var _binding: FragmentGenderAnalyticsBinding? = null
    private val binding: FragmentGenderAnalyticsBinding
        get() = _binding ?: throw RuntimeException("FragmentGenderAnalyticsBinding = null!")


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGenderAnalyticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.btBack.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun observeViewModel() {
        usersListViewModel.getAllUsers().observe(viewLifecycleOwner) { users ->
            countAge(users)
        }
    }

    private fun countAge(users: List<User>) {
        val validUsers = users.filter { it.gender != "none" }
        val menCount = validUsers.filter { it.gender == "m" }.size
        val womenCount = validUsers.filter { it.gender == "w" }.size
        binding.tvMenCount.text = menCount.toString()
        binding.tvWomenCount.text = womenCount.toString()
        binding.tvMenCountBottom.text = menCount.toString()
        binding.tvWomenCountBottom.text = womenCount.toString()
        setupCountViews(menCount,womenCount)
    }

    private fun setupCountViews(menCount: Int, womenCount: Int) {
        if (menCount>womenCount) {
            val percent = womenCount.toDouble()/menCount
            binding.viewWomenCount.layoutParams.height = (binding.viewMenCount.height*percent).toInt()
            binding.viewWomenCount.requestLayout()
        }
        else if (menCount<womenCount){
            val percent = menCount.toDouble()/womenCount
            binding.viewMenCount.layoutParams.height = (binding.viewWomenCount.height*percent).toInt()
            binding.viewMenCount.requestLayout()
        }
    }

    companion object {

        fun newInstance() =
            GenderAnalyticsFragment()
    }
}