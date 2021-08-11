package com.example.kikyui.fragment

import android.R.attr.spacing
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kikyui.GridSpacingItemDecoration
import com.example.kikyui.R
import com.example.kikyui.adapters.GddessAdapter
import kotlinx.android.synthetic.main.fragment_gddess.*
import kotlinx.android.synthetic.main.fragment_gddess.view.*


class GddessFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_gddess, container, false)

        view.rvGddess.addItemDecoration(GridSpacingItemDecoration(2, 50, true))

        view.rvGddess.layoutManager=GridLayoutManager(activity,2)
        view.rvGddess.adapter=GddessAdapter()
        return view
    }

}