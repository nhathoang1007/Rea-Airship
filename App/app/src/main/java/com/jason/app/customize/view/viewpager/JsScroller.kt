package com.jason.app.customize.view.viewpager

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

class JsScroller @JvmOverloads constructor(
    context: Context,
    interpolator: Interpolator? = null
) : Scroller(context, interpolator, true) {

    private var mScrollFactor = 1.0

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, (duration * mScrollFactor).toInt())
    }

    fun setScrollFactor(factor: Double) {
        this.mScrollFactor = factor
    }
}