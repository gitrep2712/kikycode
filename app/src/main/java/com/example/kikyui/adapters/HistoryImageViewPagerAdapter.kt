package com.example.kikyui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kikyui.fragment.HistoryImageViewerFragment


class HistoryImageViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val baseId: Long = 0
//    private val pageIds= docSingleton.data.map { it.hashCode().toLong()}

        override fun getItemCount(): Int {
            return 20
//        return docSingleton.data.size
    }

    override fun createFragment(position: Int): Fragment {
        return HistoryImageViewerFragment(position)
    }
//
//    override fun getItemId(position: Int): Long {
//        return docSingleton.data[position].hashCode().toLong()
//    }
//    override fun containsItem(itemId: Long): Boolean {
//        return pageIds.contains(itemId)
//    }


}