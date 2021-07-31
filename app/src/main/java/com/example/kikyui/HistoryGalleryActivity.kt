package com.example.kikyui

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.viewpager2.widget.ViewPager2
import com.example.kikyui.adapters.HistoryImageViewPagerAdapter
import com.example.kikyui.adapters.SmallImageAdapter
import kotlinx.android.synthetic.main.activity_doc_view.*
import java.lang.reflect.Field


class HistoryGalleryActivity : AppCompatActivity(), SmallImageAdapter.SmallImageOnclick,DialogInterface.OnDismissListener
//    DeleteDialog.DialogInterface
{
    lateinit var adapter: HistoryImageViewPagerAdapter
    lateinit var smallAdapter:SmallImageAdapter
    private  val snapHelper= CenterSnapHelper()

    lateinit var layoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_view)



        layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rvSmallImage.layoutManager = layoutManager
        smallAdapter = SmallImageAdapter(this, this)
        rvSmallImage.adapter = smallAdapter

        docViewPager.setOnClickListener {
            Toast.makeText(this, "long press ", Toast.LENGTH_SHORT).show()
        }
        docViewPager.setOnLongClickListener {
            Toast.makeText(this, "long press ", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }
        adapter = HistoryImageViewPagerAdapter(this)
        docViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        docViewPager.adapter = adapter
        docViewPager.currentItem = 0
        docViewPager.offscreenPageLimit= 10

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        docViewPager.setPageTransformer(pageTransformer)
        try {
            val recyclerViewField: Field = ViewPager2::class.java.getDeclaredField("mRecyclerView")
            recyclerViewField.isAccessible = true
            val recyclerView = recyclerViewField.get(docViewPager)
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
        snapHelper.attachToRecyclerView(rvSmallImage)

        //This is used to center first and last item on screen

        //This is used to center first and last item on screen
        rvSmallImage.addItemDecoration(object : ItemDecoration() {
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
                    } else {
                        outRect.right = padding
                    }
                }
            }
        })
//        rvSmallImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                rvSmallImage.post {
//                    selectMiddleItem()
//                }
//            }
//        })
//        docViewPager.addItemDecoration(itemDecoration)
        docViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {


                rvSmallImage.scrollToPosition(position)
            }


            override fun onPageSelected(position: Int) {
            }
        })



//        btnBack.setOnClickListener { finish() }

    }

    override fun onClick(position: Int) {
        docViewPager.currentItem = position
    }

    class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }

    }

    private fun selectMiddleItem() {
        val firstVisibleIndex = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleIndex = layoutManager.findLastVisibleItemPosition()
        val visibleIndexes = listOf(firstVisibleIndex..lastVisibleIndex).flatten()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        for (i in visibleIndexes) {
            val vh = rvSmallImage.findViewHolderForLayoutPosition(i)
            if (vh?.itemView == null) {
                continue
            }
            val location = IntArray(2)
            vh.itemView.getLocationOnScreen(location)
            val x = location[0]
            val halfWidth = vh.itemView.width * .5
            val rightSide = x + halfWidth
            val leftSide = x - halfWidth
            val isInMiddle = width * .5 in leftSide..rightSide
            if (isInMiddle) {
                // "i" is your middle index and implement selecting it as you want
                rvSmallImage.scrollToPosition(i)
                return
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        adapter.notifyDataSetChanged()
    }


}
