package dev.danilovteodoro.placesapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.danilovteodoro.placesapp.R
import dev.danilovteodoro.placesapp.databinding.ActivityMainBinding
import dev.danilovteodoro.placesapp.model.Event
import dev.danilovteodoro.placesapp.ui.adapter.EventAdapter
import dev.danilovteodoro.placesapp.ui.viewmodel.EventStateListener
import dev.danilovteodoro.placesapp.ui.viewmodel.EventViewModel
import util.DataState

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
        binding.rcEvents.layoutManager = LinearLayoutManager(this)
        adapter = EventAdapter(this)
        binding.rcEvents.adapter = adapter
        registerObserver()

        viewModel.callStateListener(EventStateListener.GetEvents)
    }

    private fun registerObserver(){
        viewModel.eventsLv.observe(this, Observer { dataState->
            when(dataState){
                is DataState.Success -> {
                    adapter.add(dataState.value)
                }
            }
        })
    }

   
}