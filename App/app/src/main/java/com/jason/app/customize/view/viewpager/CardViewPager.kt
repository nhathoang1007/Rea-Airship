package com.jason.app.customize.view.viewpager

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.Interpolator
import androidx.viewpager.widget.ViewPager
import com.jason.app.customize.view.card.CardView
import com.jason.app.customize.view.card.CardView.Companion.CARD_MARGIN_TO_TOP
import com.jason.app.extensions.dp
import java.lang.reflect.Field

class CardViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    private lateinit var cardView: CardView
    private var mScroller: JsScroller? = null
    private var onPagerChanged: ((Int) -> Unit)? = null
    private var onFlyingChanged: ((Boolean) -> Unit)? = null

    init {
        kotlin.runCatching {
            val scroller: Field = this::class.java.getDeclaredField("mScroller").apply {
                isAccessible = true
            }
            val interpolator: Field = this::class.java.getDeclaredField("sInterpolator").apply {
                isAccessible = true
            }
            mScroller = JsScroller(context, interpolator.get(null) as Interpolator).apply {
                setScrollFactor(2.0)
            }
            scroller.set(this, mScroller)
        }

        addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                cardView.onAnimateHorizontal(positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                onPagerChanged?.invoke(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isAbleToMove(ev) && super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return isAbleToMove(ev) && super.onTouchEvent(ev)
    }

    private var isAbleToMove = true
    private fun isAbleToMove(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                val rawY = ev.rawY - CARD_MARGIN_TO_TOP.dp
                val xAble = ev.rawX > cardView.x && ev.rawX < cardView.measuredWidth + cardView.x
                val yAble = rawY > cardView.y && rawY < cardView.measuredHeight + cardView.y
                isAbleToMove = xAble && yAble
            }

            MotionEvent.ACTION_MOVE -> {
                onFlyingChanged?.invoke(isAbleToMove)
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                onFlyingChanged?.invoke(false)
            }
        }
        return isAbleToMove
    }

    fun initCardView(cardView: CardView) {
        this.cardView = cardView
    }

    fun initPagerChangedCallback(onPagerChanged: (Int) -> Unit) {
        this.onPagerChanged = onPagerChanged
    }

    fun initFlyingChangedCallback(onFlyingChanged: (Boolean) -> Unit) {
        this.onFlyingChanged = onFlyingChanged
    }
}