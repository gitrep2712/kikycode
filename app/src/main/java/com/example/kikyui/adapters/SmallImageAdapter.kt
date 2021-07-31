package com.example.kikyui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kikyui.R
import kotlinx.android.synthetic.main.small_image_item.view.*

class SmallImageAdapter(val listener:SmallImageOnclick,val context: Context): RecyclerView.Adapter<SmallImageAdapter.ViewHolder>(){
    interface SmallImageOnclick{
        fun onClick(position: Int)

    }
    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item)
    lateinit var smallImageOnclick: SmallImageOnclick
    private var selected=false
    private var pos =0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.small_image_item, parent, false)
        val viewHolder = ViewHolder(view)
        smallImageOnclick=listener
        return viewHolder
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.smallImageLayout.setOnClickListener {
                smallImageOnclick.onClick(position)
            }
//        HelperFunctions.loadImageWithGlide(context,docSingleton.data[position].thumbnail,holder.itemView.ivSmall)

    }
    fun updateView(selected: Boolean,position: Int){
        if(selected){
            this.selected=selected
            pos=position
            notifyDataSetChanged()
        }

    }


}