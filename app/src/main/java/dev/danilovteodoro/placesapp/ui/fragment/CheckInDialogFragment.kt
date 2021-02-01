package dev.danilovteodoro.placesapp.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import dev.danilovteodoro.placesapp.R
import dev.danilovteodoro.placesapp.databinding.CheckinFragmentBinding
import dev.danilovteodoro.placesapp.model.CheckIn
import dev.danilovteodoro.placesapp.util.ValidateUtil

class CheckInDialogFragment: DialogFragment() {
    private var callback:((checkIn:CheckIn)->Unit)? = null
    private var _binding:CheckinFragmentBinding? = null
    private val binding:CheckinFragmentBinding  get() = _binding!!
    private lateinit var eventId: String

    companion object {
        fun show(eventId:String,fragmentManager: FragmentManager,callback:(checkIn:CheckIn)->Unit){
            val fragment = CheckInDialogFragment()
            fragment.callback = callback
            fragment.eventId = eventId
            fragment.show(fragmentManager,"")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CheckinFragmentBinding.inflate(inflater)
        binding.btnCheckIn.setOnClickListener {
            doCheckIn()
        }
        binding.imgClose.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun doCheckIn(){
       if(validate()){
           val checkIn = CheckIn(
                   eventId,
                   binding.editName.text.toString(),
                   binding.editEmail.text.toString()
           )
           callback?.invoke(checkIn)
           this.dismiss()
       }
    }


    private fun validate():Boolean{
        if(binding.editName.text.toString().isBlank()){
            binding.txtErrorMessage.text = getString(R.string.checkin_error_name_empty)
            binding.editName.requestFocus()
            return false
        }
        if(binding.editEmail.text.toString().isBlank()){
            binding.txtErrorMessage.text = getString(R.string.checkin_error_email_empty)
            binding.editEmail.requestFocus()
            return false
        }
        if(!ValidateUtil.isValidEmail(binding.editEmail.text.toString())){
            binding.txtErrorMessage.text = getString(R.string.checkin_error_email_invalid)
            binding.editEmail.requestFocus()
            return false
        }
        binding.txtErrorMessage.text = ""
        return true 
    }


}