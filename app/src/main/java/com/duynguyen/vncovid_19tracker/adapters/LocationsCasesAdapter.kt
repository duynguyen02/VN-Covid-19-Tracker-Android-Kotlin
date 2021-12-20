package com.duynguyen.vncovid_19tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.duynguyen.vncovid_19tracker.R
import com.duynguyen.vncovid_19tracker.models.InternalLocationsCases




class LocationsCasesAdapter(var mLocationsCasesList : List<InternalLocationsCases>) : RecyclerView.Adapter<LocationsCasesAdapter.ListItemHolder>() {

    inner class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // set id for variables from item_locations_cases layout
        var name : TextView = itemView.findViewById(R.id.tv_item_name)
        var totalCases : TextView = itemView.findViewById(R.id.tv_item_cases)
        var todayCases : TextView = itemView.findViewById(R.id.tv_item_today_cases)
        var death : TextView = itemView.findViewById(R.id.tv_item_death)
    }

    /**
     * create view for item holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        val itemView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_locations_cases, parent, false)
        return ListItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        val internalLocationsCases = mLocationsCasesList[position]
        holder.name.text = internalLocationsCases.name
        holder.todayCases.text = internalLocationsCases.caseToday
        holder.totalCases.text = internalLocationsCases.cases
        holder.death.text = internalLocationsCases.death
    }

    override fun getItemCount(): Int = mLocationsCasesList.size
}