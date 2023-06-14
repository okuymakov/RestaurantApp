package com.example.core.ui.recyclerview.itemdecorations

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridOffsetDecoration(
    horizontal : Int,
    vertical: Int
) : RecyclerView.ItemDecoration() {

    private val hSpace: Int
    private val vSpace: Int

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val params = view.layoutParams as GridLayoutManager.LayoutParams
        val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        val column =  params.spanIndex
        val pos = parent.getChildAdapterPosition(view)
        if(column != 0) {
            outRect.left = hSpace
        }
        if(column < spanCount - 1) {
            outRect.right = hSpace
        }
        if (pos >= spanCount) {
            outRect.top = vSpace
        }
        outRect.top = vSpace
        outRect.bottom = vSpace
    }

    init {
        hSpace = horizontal / 2
        vSpace = vertical / 2

    }
}