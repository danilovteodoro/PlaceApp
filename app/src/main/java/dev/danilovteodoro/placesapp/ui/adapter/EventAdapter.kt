package dev.danilovteodoro.placesapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.danilovteodoro.placesapp.R
import dev.danilovteodoro.placesapp.databinding.InflaterEventBinding
import dev.danilovteodoro.placesapp.model.Event

class EventAdapter(context:Context) : RecyclerView.Adapter<EventAdapter.ViewHolder>(){
    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private var events:List<Event> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.inflater_event,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = events[position]
        holder.binding.txtTitle.text = current.title
        Picasso.get().load(current.image)
            .into(holder.binding.imgEvent)
    }

    override fun getItemCount(): Int {
        return events.count()
    }

    fun add(events:List<Event>){
        this.events = events
        notifyDataSetChanged()
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding =  InflaterEventBinding.bind(view)
    }
}