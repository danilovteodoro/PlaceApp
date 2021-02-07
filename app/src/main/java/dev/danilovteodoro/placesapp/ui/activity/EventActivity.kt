package dev.danilovteodoro.placesapp.ui.activity

import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.danilovteodoro.placesapp.R
import dev.danilovteodoro.placesapp.databinding.ActivityEventBinding
import dev.danilovteodoro.placesapp.model.Event
import dev.danilovteodoro.placesapp.ui.fragment.CheckInDialogFragment
import dev.danilovteodoro.placesapp.ui.fragment.ErrorCheckInDialogFragment
import dev.danilovteodoro.placesapp.ui.viewmodel.EventStateListener
import dev.danilovteodoro.placesapp.ui.viewmodel.EventViewModel
import dev.danilovteodoro.placesapp.util.NumberUtil
import util.Constantes
import util.DataState
import util.DateUtil

@AndroidEntryPoint
class EventActivity : AppCompatActivity() {
    private val viewModel:EventViewModel by viewModels()
    private lateinit var eventId:String
    private var event:Event? = null
    private var _binding:ActivityEventBinding? = null
    private val binding:ActivityEventBinding get() = _binding!!

    companion object{
        private const val IT_EVENT = "itEventId"
        fun start(activity:Activity,eventId:String,activityOptions: ActivityOptions){
            val intent = Intent(activity,EventActivity::class.java)
            intent.putExtra(IT_EVENT,eventId)
            activity.startActivity(intent,activityOptions.toBundle())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar()
        eventId = intent.getStringExtra(IT_EVENT) ?: ""
        registerObserver()

        binding.btnOpenMap.setOnClickListener {
            goToMaps()
        }

        binding.fabCheckIn.setOnClickListener {
            event?.let { event ->
                CheckInDialogFragment.show(event.id,supportFragmentManager){checkIn ->
                    viewModel.callStateListener(EventStateListener.PostCheckIn(checkIn))
                }
            }
        }

        if(viewModel.selectedEventLv.value == null){
            viewModel.callStateListener(EventStateListener.GetEvent(eventId))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerObserver(){
        viewModel.selectedEventLv.observe(this, { dataState->
            when(dataState){
                is DataState.Loading ->{
                    showProgress()
                }

                is DataState.Success -> {
                       hideProgress()
                       event = dataState.value
                       updateLayout()
                }

                is DataState.NoConnection -> {
                    hideProgress()
                    showError(getString(R.string.error_network),R.drawable.ic_network)
                }

                is DataState.ServerError -> {
                    hideProgress()
                    showError(getString(R.string.error_server),R.drawable.ic_error_outline)
                }

                is DataState.TimeoutError -> {
                    hideProgress()
                    showError(getString(R.string.error_timeout),R.drawable.ic_error_outline)
                }

                is DataState.Error -> {
                    hideProgress()
                    showError(getString(R.string.error_generic),R.drawable.ic_error_outline)
                }
            }
        })


        viewModel.checkInLv.observe(this, { dataState->
            when(dataState){
                is DataState.Loading ->{
                    showProgress()
                }

                is DataState.Success -> {
                    hideProgress()
                    if (dataState.value){
                        showCheckInCompleted()
                    }
                }

                is DataState.NoConnection -> {
                    hideProgress()
                    showError(getString(R.string.error_network),R.drawable.ic_network)
                }

                is DataState.ServerError -> {
                    hideProgress()
                    dataState.e.response()?.errorBody()?.string()?.let { e->
                        ErrorCheckInDialogFragment.show(supportFragmentManager, e)
                    }
                }

                is DataState.TimeoutError -> {
                    hideProgress()
                    showError(getString(R.string.error_timeout),R.drawable.ic_error_outline)
                }

                is DataState.Error -> {
                    hideProgress()
                    showError(getString(R.string.error_generic),R.drawable.ic_error_outline)
                }
            }
        })
    }

    private fun setupToolBar(){
        setSupportActionBar(binding.appbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.collapse.setExpandedTitleColor(Color.TRANSPARENT)
    }

    private fun goToMaps(){
        event?.let { event ->
            val uri = Uri.parse(
                "geo:"+event.latitude+","+
                        event.longitude+"?q="+
                        event.latitude+","+
                        event.longitude+
                        "("+Uri.encode(event.title)+")"
            )
            val intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }
    }

    private fun updateLayout(){
        event?.let { event ->
            Picasso.get().load(event.image)
                .placeholder(R.drawable.ic_photo)
                .into(binding.imgCollapse)

            // update Title
            binding.txtPlaceName.text = event.title
            binding.collapse.title = event.title
            title = event.title

            //update values
            binding.txtDataHora.text = DateUtil.format(event.date,Constantes.DATE_FORMAT)
            binding.txtPrice.text = NumberUtil.getCurrency(event.price)
            binding.txtDescription.text = event.description
        }
    }

   private fun showProgress(){
       binding.mainLayout.visibility = View.GONE
       binding.fabCheckIn.visibility = View.GONE
       binding.progress.visibility = View.VISIBLE
    }
    private fun hideProgress(){
       binding.mainLayout.visibility = View.VISIBLE
        binding.fabCheckIn.visibility = View.VISIBLE
       binding.progress.visibility = View.GONE
    }

    private fun showError(errorMessage:String,errorIcon:Int){
        binding.layoutError.main.visibility = View.VISIBLE
        binding.mainLayout.visibility = View.GONE
        binding.fabCheckIn.visibility = View.VISIBLE
        binding.layoutError.txtErrorMessage.text = errorMessage
        binding.layoutError.imgError.setImageResource(errorIcon)
    }



    private fun showCheckInCompleted(){
        val alert = AlertDialog.Builder(this)
        alert.setMessage(R.string.success_checkin)
        alert.setNeutralButton("Ok",null)
        alert.show()
    }
}