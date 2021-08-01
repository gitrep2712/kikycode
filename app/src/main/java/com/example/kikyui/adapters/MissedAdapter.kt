package com.example.kikyui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kikyui.R

class MissedAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissedVH {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return MissedVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class MissedVH(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
//            textView = view.findViewById(R.id.textView)
        }
    }
}