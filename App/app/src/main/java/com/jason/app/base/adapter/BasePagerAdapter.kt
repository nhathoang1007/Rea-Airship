package com.jason.app.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jason.app.base.view.BaseFragment

open class BasePagerAdapter<T: BaseFragment<*,*>>(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val fragments = mutableListOf<T>()

    override fun getCount() = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    fun addFragments(fragments: MutableList<T>) {
        this.fragments.apply {
            clear()
            addAll(fragments)
            notifyDataSetChanged()
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}