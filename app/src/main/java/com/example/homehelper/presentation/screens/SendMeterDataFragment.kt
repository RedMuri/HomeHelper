package com.example.homehelper.presentation.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.homehelper.R
import com.example.homehelper.databinding.FragmentEventsBinding
import com.example.homehelper.databinding.FragmentSendMeterDataBinding
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class SendMeterDataFragment : Fragment() {

    private var _binding: FragmentSendMeterDataBinding? = null
    private val binding: FragmentSendMeterDataBinding
        get() = _binding ?: throw RuntimeException("FragmentSendMeterDataBinding = null!")

    private lateinit var getImageLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        getImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
        ) { result ->
            this.onGetImageResult(result)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSendMeterDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btAddDataImage.setOnClickListener {
            openGetImageActivity()
        }
    }


    private fun openGetImageActivity() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)

        getImageLauncher.launch(intent)
    }

    private fun onGetImageResult(result: ActivityResult) {
        val uri = result.data?.data
        Picasso.get().load(uri).into(binding.ivData)
    }


    companion object {

        fun newInstance() =
            SendMeterDataFragment()
    }
}