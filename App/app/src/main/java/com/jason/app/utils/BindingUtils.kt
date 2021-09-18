package com.jason.app.utils

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.jason.app.base.adapter.BasePagerAdapter
import com.jason.app.extensions.getDefault
import com.jason.app.extensions.isEnable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.atomic.AtomicBoolean


class OnSingleClickListener(
    private val clickListener: View.OnClickListener,
    private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)
    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                clickListener.onClick(v)
            }
        }
    }
}

object BindingUtils {

    @BindingAdapter("app:onDelayClicked")
    @JvmStatic
    fun View.onDelayClicked(onClick: View.OnClickListener?) {
        onClick?.also {
            setOnClickListener(OnSingleClickListener(it))
        } ?: setOnClickListener(null)
    }

    @BindingAdapter("app:isVisible")
    @JvmStatic
    fun View.isVisible(isVisible: Boolean?) {
        animate().alpha(if (isVisible.getDefault()) 1.0f else 0.0f).setDuration(300)
            .withStartAction {
                if (isVisible == true) {
                    visibility = View.VISIBLE
                }
            }
            .withEndAction {
                if (isVisible != true) {
                    visibility = View.GONE
                }
            }.start()
    }

    @BindingAdapter("app:setAlpha")
    @JvmStatic
    fun View.setAlpha(float: Float?) {
        alpha = float ?: 1.0f
    }

    @BindingAdapter("app:isSelected")
    @JvmStatic
    fun View.isSelected(isSelected: Boolean?) {
        this.isSelected = isSelected ?: false
    }

    @BindingAdapter("app:bindTo")
    @JvmStatic
    fun AppCompatButton.bindTo(subject: PublishSubject<Boolean>) {
        subject.subscribe(this.isEnable())
    }

    /*Recycler View*/
    @BindingAdapter("app:initLinear")
    @JvmStatic
    fun RecyclerView.initLinear(type: Int = RecyclerView.HORIZONTAL) {
        this.layoutManager = LinearLayoutManager(this.context, type, false)
    }

    @BindingAdapter("app:initGrid")
    @JvmStatic
    fun RecyclerView.initGrid(size: Int) {
        if (size <= 0) return
        this.layoutManager = GridLayoutManager(this.context, size)
    }

    @BindingAdapter("app:bindAdapter")
    @JvmStatic
    fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>) {
        this.adapter = adapter
    }

    /*ViewPager*/
    @BindingAdapter("app:bindAdapter")
    @JvmStatic
    fun ViewPager.bindAdapter(adapter: PagerAdapter) {
        this.adapter = adapter
    }
}