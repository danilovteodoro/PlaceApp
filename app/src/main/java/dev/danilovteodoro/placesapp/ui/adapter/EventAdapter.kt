package dev.danilovteodoro.placesapp.ui.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.danilovteodoro.placesapp.R
import dev.danilovteodoro.placesapp.databinding.InflaterEventBinding
import dev.danilovteodoro.placesapp.model.Event
import dev.danilovteodoro.placesapp.util.NumberUtil
import util.Constantes
import util.DateUtil

class EventAdapter(context:Context,val headerText:String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private var events:List<Event> = emptyList()
    private val CONTENT_TYPE = 1
    private val HEADER_TYPE = 2

    override fun getItemViewType(position: Int): Int {
        return if(position == 0) HEADER_TYPE else CONTENT_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CONTENT_TYPE){
            val view = inflater.inflate(R.layout.inflater_event, parent,false)
            ViewHolder(view)
         }else{
            val view = inflater.inflate(R.layout.header_layout, parent,false)
            HeaderViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is EventAdapter.ViewHolder){
            val current = events[position-1]
            holder.binding.txtTitle.text = current.title
            holder.binding.txtDescription.text = Html.fromHtml(current.description)
            Picasso.get().load(current.image)
                    .placeholder(R.drawable.ic_photo)
                    .into(holder.binding.imgEvent)
            holder.binding.txtDataHora.text = DateUtil.format(current.date, Constantes.DATE_FORMAT)
            holder.binding.txtPrice.text = NumberUtil.getCurrency(current.price)
        }
    }

    override fun getItemCount(): Int {
        return events.count() +1
    }

    fun add(events:List<Event>){
        this.events = events
        notifyDataSetChanged()
    }

    fun get(position: Int):Event{
        return this.events[position - 1]
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding =  InflaterEventBinding.bind(view)
    }
    inner class HeaderViewHolder(view: View):RecyclerView.ViewHolder(view) {
        init {
            view.findViewById<TextView>(R.id.txtHeader)
                    .text = headerText
        }
    }


}