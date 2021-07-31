package com.example.kikyui.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kikyui.AccountSettingActivity
import com.example.kikyui.GddesActivity
import com.example.kikyui.HistoryActivity
import com.example.kikyui.HistoryGalleryActivity
import com.example.kikyui.databinding.FragmentExploreBinding

class ExploreFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentExploreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val btnProfile = binding.btnProfile
        val btnHistory= binding.btnHistory
        val btnGddess=binding.btnGddes

        btnProfile.setOnClickListener{
            val intent = Intent(activity,AccountSettingActivity::class.java)
            startActivity(intent)
        }
        btnHistory.setOnClickListener {
            val intent = Intent(activity, HistoryGalleryActivity::class.java)
            startActivity(intent)
        }
        btnGddess.setOnClickListener {
            val intent=Intent(activity,GddesActivity::class.java)
            startActivity(intent)
        }
//

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}