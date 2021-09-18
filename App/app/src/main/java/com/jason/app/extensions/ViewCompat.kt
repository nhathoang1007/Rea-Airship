package com.jason.app.extensions

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.jason.app.R
import com.jason.app.customize.DividerWithoutLastItemDecorator

/**
 * Created by Nhat Vo on 14/06/2021.
 */


fun RecyclerView.addDivider(padding: Int = 0) {
    val dividerDrawable = ContextCompat.getDrawable(context, R.drawable.divider)
    dividerDrawable?.let {
        this.addItemDecoration(
            DividerWithoutLastItemDecorator(it, padding.convertDpToPx())
        )
    }
}