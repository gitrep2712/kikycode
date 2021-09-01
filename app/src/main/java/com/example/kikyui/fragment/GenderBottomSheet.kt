package com.example.kikyui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kikyui.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.gender_bottom_sheet.*
import java.lang.RuntimeException


class GenderBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    private var mListener: ItemClickListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.gender_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvMale.setOnClickListener {
            cvMale.background
        }
//        view.findViewById<View>(R.id.textView).setOnClickListener(this)
//        view.findViewById<View>(R.id.textView2).setOnClickListener(this)
//        view.findViewById<View>(R.id.textView3).setOnClickListener(this)
//        view.findViewById<View>(R.id.textView4).setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is ItemClickListener) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement ItemClickListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onClick(view: View) {
        val tvSelected = view as TextView
        mListener!!.onItemClick(tvSelected.text.toString())
        dismiss()
    }

    interface ItemClickListener {
        fun onItemClick(item: String?)
    }

//    companion object {
//        const val TAG = "ActionBottomDialog"
//        fun newInstance(): GenderBottomSheet {
//            return newInstance()
//        }
//    }
}