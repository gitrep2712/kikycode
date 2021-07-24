package com.example.kikyui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.kikyui.MessageActivity
import com.example.kikyui.R

class MessageListAdapter(val context: Context): RecyclerView.Adapter<MessageListAdapter.ListVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListVH {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_list_item, parent, false)
        return ListVH(view)
    }

    override fun onBindViewHolder(holder: ListVH, position: Int) {
        holder.parentLayout.setOnClickListener {
            val intent= Intent(context,MessageActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return 10
    }

    class ListVH(view: View) : RecyclerView.ViewHolder(view) {
        val parentLayout:ConstraintLayout = view.findViewById(R.id.parentLayout)

    }
}