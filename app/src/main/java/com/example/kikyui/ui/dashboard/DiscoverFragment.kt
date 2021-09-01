package com.example.kikyui.ui.dashboard

import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.kikyui.CenterSnapHelper
import com.example.kikyui.R
import com.example.kikyui.adapters.HistoryImageViewPagerAdapter
import com.example.kikyui.adapters.SmallImageAdapter
import com.example.kikyui.databinding.FragmentDiscoverBinding
import kotlinx.android.synthetic.main.activity_doc_view.*
import kotlinx.android.synthetic.main.fragment_discover.view.*
import java.lang.reflect.Field

class DiscoverFragment : Fragment(), SmallImageAdapter.SmallImageOnclick, DialogInterface.OnDismissListener {
    lateinit var adapter: HistoryImageViewPagerAdapter
    lateinit var smallAdapter:SmallImageAdapter
    private  val snapHelper= CenterSnapHelper()
    var padding:Int=0


    lateinit var layoutManager: LinearLayoutManager
    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDiscoverBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val elementWidth =
            resources.getDimension(R.dimen.list_item_width).toInt()
        val elementMargin =
            resources.getDimension(R.dimen.list_item_margin).toInt()
        padding = Resources.getSystem().displayMetrics.widthPixels / 2 - elementWidth - elementMargin / 2

        layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
       root.rvSmallImage.layoutManager = layoutManager
        smallAdapter = activity?.let { SmallImageAdapter(this, it) }!!
        root.rvSmallImage.adapter = smallAdapter

//        docViewPager.setOnClickListener {
//            Toast.makeText(this, "long press ", Toast.LENGTH_SHORT).show()
//        }
//        docViewPager.setOnLongClickListener {
//            Toast.makeText(this, "long press ", Toast.LENGTH_SHORT).show()
//            return@setOnLongClickListener true
//        }
        adapter = activity?.let { HistoryImageViewPagerAdapter(it) }!!
        root.docViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        root.docViewPager.adapter = adapter
        root.docViewPager.currentItem = 0
        root.docViewPager.offscreenPageLimit= 10

//        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val width = (resources.displayMetrics.widthPixels * 0.13).toInt()

        val currentItemHorizontalMarginPx = width
//            resources.getDimension(width)
        val pageTranslationX =   currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        root.docViewPager.setPageTransformer(pageTransformer)
        try {
            val recyclerViewField: Field = ViewPager2::class.java.getDeclaredField("mRecyclerView")
            recyclerViewField.isAccessible = true
            val recyclerView = recyclerViewField.get(root.docViewPager)
            val touchSlopField: Field = RecyclerView::class.java.getDeclaredField("mTouchSlop")
            touchSlopField.isAccessible = true

            val touchSlop = touchSlopField[recyclerView] as Int

            touchSlopField.set(
                recyclerView,
                touchSlop * 6
            ) //6 is empirical value
        } catch (ignore: Exception) {
        }
//        var rawX=0.0f
//        val mTouchSlop = ViewConfiguration.get(this).scaledTouchSlop
//
//        docViewPager.setOnTouchListener { v, event ->
//           when (event.actionMasked) {
//               MotionEvent.ACTION_DOWN -> {
//                   v.parent.requestDisallowInterceptTouchEvent(true)
//                   rawX = event.rawX
//               }
//               MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
//                   v.parent.requestDisallowInterceptTouchEvent(false)
//                   rawX = 0f
//               }
//               MotionEvent.ACTION_MOVE -> if (Math.abs(rawX - event.rawX) > mTouchSlop) v.parent
//                   .requestDisallowInterceptTouchEvent(true)
//           }
//           return@setOnTouchListener false
//       }
// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
//        val itemDecoration = HorizontalMarginItemDecoration(
//            this,
//            R.dimen.viewpager_current_item_horizontal_margin
//        )

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(root.rvSmallImage)
        root.rvSmallImage.onFlingListener=snapHelper

        //This is used to center first and last item on screen

////        //This is used to center first and last item on screen
        root.rvSmallImage.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildViewHolder(view).adapterPosition

                if (position == 0 || position == state.itemCount - 1) {
                    val elementWidth =
                        resources.getDimension(R.dimen.list_item_width).toInt()
                    val elementMargin =
                        resources.getDimension(R.dimen.list_item_margin).toInt()
                    val padding: Int = Resources.getSystem()
                        .displayMetrics.widthPixels / 2 - elementWidth - elementMargin / 2
                    if (position == 0) {
                        outRect.left = padding
                    }
//                    else
//                    {
//                        outRect.right = padding
//                    }
                }
            }
        })
//
//        rvSmallImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//
//                rvSmallImage.post {
//                    selectMiddleItem()
//                }
//            }
//        })
//        docViewPager.addItemDecoration(itemDecoration)
        root.docViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {


//                rvSmallImage.scrollToPosition(position)

                val width = (resources.displayMetrics.widthPixels )

                val view: View = root.rvSmallImage.getChildAt(position) ?: return
                var scrollX= view.left - (width / 2) + (view.width / 2)


                smallAdapter.updateView(true,position)


                root.rvSmallImage.smoothScrollBy(scrollX, 0)
            }


            override fun onPageSelected(position: Int) {
                val pos = position
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int) {
        docViewPager.currentItem = position

    }

    override fun onDismiss(dialog: DialogInterface?) {
    }
}