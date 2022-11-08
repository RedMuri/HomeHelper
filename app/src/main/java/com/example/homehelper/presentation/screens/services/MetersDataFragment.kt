package com.example.homehelper.presentation.screens.services

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homehelper.R

class MetersDataFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.fragment_meters_data, container, false)
    }

    companion object {

        fun newInstance() =
            MetersDataFragment()
    }
}