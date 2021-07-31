package com.example.kikyui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.kikyui.R
import kotlinx.android.synthetic.main.fragment_pdf_viewer.view.*

class HistoryImageViewerFragment(var position: Int) : Fragment() {

    lateinit var mContext: Context
    lateinit var ivAnimationHand: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pdf_viewer, container, false)


        return view

    }










    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

    }

    fun animateHand() {
        ivAnimationHand.visibility = View.VISIBLE

        val animUpDown = loadAnimation(
            context,
            R.anim.updown
        )

        animUpDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                ivAnimationHand.visibility = View.GONE

            }

            override fun onAnimationEnd(animation: Animation?) {
                ivAnimationHand.clearAnimation()
                ivAnimationHand.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })

        // start the animation
        ivAnimationHand.startAnimation(animUpDown)
    }
//
    override fun onPause() {
        super.onPause()

    }



}
