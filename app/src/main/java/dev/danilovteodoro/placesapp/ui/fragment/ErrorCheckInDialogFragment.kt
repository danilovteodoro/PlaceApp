package dev.danilovteodoro.placesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import dev.danilovteodoro.placesapp.databinding.ErrorCheckinBinding

class ErrorCheckInDialogFragment :DialogFragment(){
    private var _binding:ErrorCheckinBinding? = null
    private val binding:ErrorCheckinBinding get() = _binding!!
    private var error:String = ""

    companion object {
        fun show(fragmentManager: FragmentManager,errorMessage:String){
            val fragment = ErrorCheckInDialogFragment()
            fragment.error = errorMessage
            fragment.show(fragmentManager,"")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ErrorCheckinBinding.inflate(inflater)
        binding.txtErrorMessage.text = error
        binding.btnOk.setOnClickListener {
            dismiss()
        }
        binding.imgClose.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}