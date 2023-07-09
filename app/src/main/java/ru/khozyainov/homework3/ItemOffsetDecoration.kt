package ru.khozyainov.homework3

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemOffsetDecoration(
    private val context: Context,
    private val dp: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {

        val offset = dp.fromDpToPixels(context)
        with(outRect) {
            top = offset
            right = offset
            left = offset
            bottom = offset
        }
    }

    private fun Int.fromDpToPixels(context: Context): Int =
        context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT * this

}