package dev.danilovteodoro.placesapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.danilovteodoro.placesapp.R
import dev.danilovteodoro.placesapp.databinding.ActivityMainBinding
import dev.danilovteodoro.placesapp.ui.adapter.EventAdapter
import dev.danilovteodoro.placesapp.ui.viewmodel.EventStateListener
import dev.danilovteodoro.placesapp.ui.viewmodel.EventViewModel
import util.DataState
import util.onItemTouch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel:EventViewModel by viewModels()
    private var _binding:ActivityMainBinding? = null
    private val binding:ActivityMainBinding get() = _binding!!
    private lateinit var adapter:EventAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appbar)
        binding.rcEvents.layoutManager = LinearLayoutManager(this)
        adapter = EventAdapter(this,getString(R.string.header_events))
        binding.rcEvents.adapter = adapter
        binding.rcEvents.onItemTouch { position, _ ->
            EventActivity.start(this,
            adapter.get(position).id)
        }
        binding.layoutError.btnRetry.setOnClickListener {
            hideError()
            viewModel.callStateListener(EventStateListener.GetEvents)
        }
        registerObserver()

        if(viewModel.eventsLv.value == null){
            viewModel.callStateListener(EventStateListener.GetEvents)
        }
    }

    private fun registerObserver(){
        viewModel.eventsLv.observe(this, Observer { dataState->
            when(dataState){
                is DataState.Loading -> {
                    showProgress()
                }
                is DataState.Success -> {
                    hideProgress()
                    adapter.add(dataState.value)
                }
                is DataState.NoConnection -> {
                    hideProgress()
                    showError(getString(R.string.error_network),R.drawable.ic_network)
                }
                is DataState.ServerError-> {
                    hideProgress()
                    showError(getString(R.string.error_server),R.drawable.ic_error_outline)
                }
                is DataState.TimeoutError ->{
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



    private fun showProgress(){
        binding.rcEvents.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
    }
    private fun hideProgress(){
        binding.rcEvents.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
    }

    private fun showError(errorMessage:String,errorIcon:Int){
        binding.rcEvents.visibility = View.GONE
        binding.layoutError.main.visibility = View.VISIBLE
        binding.layoutError.txtErrorMessage.text  =  errorMessage
        binding.layoutError.imgError.setImageResource(errorIcon)

    }

    fun hideError(){
        binding.rcEvents.visibility = View.VISIBLE
        binding.layoutError.main.visibility = View.GONE
    }
}