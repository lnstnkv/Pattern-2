package ru.tsu.bank.details

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.tsu.bank.R

class HistoryItemDecorator  : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return
        val margin4 = parent.context.resources.getDimensionPixelSize(R.dimen.margin4)
        val margin8 = parent.context.resources.getDimensionPixelSize(R.dimen.margin8)
        val margin16 = parent.context.resources.getDimensionPixelSize(R.dimen.margin16)
        var margin32 = parent.context.resources.getDimensionPixelSize(R.dimen.margin32)
        val newRect = when (position) {
            0 -> Rect(margin8, margin8, margin8, margin4)
            state.itemCount - 1 -> Rect(margin8, margin4, margin8, margin8)
            else ->Rect(margin8, margin4, margin8, margin4)
        }
        outRect.apply {
            left=newRect.left
            right= newRect.right
            top=newRect.top
            bottom=newRect.bottom
        }
    }
}