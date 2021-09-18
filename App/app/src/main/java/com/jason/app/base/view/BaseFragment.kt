package com.jason.app.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jason.app.MyApp
import com.jason.app.base.viewmodel.BaseViewModel
import com.jason.app.extensions.applicationContext

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment(), BaseFragmentView<VM> {

    protected val TAG = this::class.simpleName

    protected lateinit var dataBinding: V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = getViewBinding(container, savedInstanceState)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        return dataBinding.root
    }

    abstract fun getViewBinding(container: ViewGroup?, savedInstanceState: Bundle?): V

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    open fun initView() {}

    open fun initViewModel() {
        mViewModel.apply {
            isLoadingObs.observe(viewLifecycleOwner, {
                handleLoading(it)
            })
            errorObs.observe(viewLifecycleOwner, {
                handleError(it)
            })
        }
    }

    override fun handleError(error: Throwable?) {
        getParentView().handleError(error)
    }

    override fun handleLoading(isLoading: Boolean?) {
        getParentView().handleLoading(isLoading)
    }

    override val mContext: Context = applicationContext()

    override fun getParentView() = requireActivity() as BaseActivityView<*>
}