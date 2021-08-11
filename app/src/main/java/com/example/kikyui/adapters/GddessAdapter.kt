package com.example.kikyui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kikyui.R

class GddessAdapter : RecyclerView.Adapter<GddessAdapter.GddessVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GddessVH {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gddess_item, parent, false)
        return GddessVH(view)
    }

    override fun onBindViewHolder(holder: GddessVH, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    class GddessVH(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
//            textView = view.findViewById(R.id.textView)
        }
    }
}