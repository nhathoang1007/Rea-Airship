package com.jason.app.view.account.manage

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jason.app.databinding.FragmentCardManagementBinding
import com.jason.app.view.account.BaseAccountFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardManagementFragment :
    BaseAccountFragment<FragmentCardManagementBinding, CardManagementViewModel>(),
    CardManagementView {

    override val settingAdapter: AccountSettingAdapter by lazy {
        AccountSettingAdapter()
    }

    override val mViewModel: CardManagementViewModel by viewModel()

    override fun getViewBinding(
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCardManagementBinding.inflate(layoutInflater)

    override fun initView() {
        super.initView()
        dataBinding.apply {
            iView = this@CardManagementFragment
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        mViewModel.settingOptionObs.observe(viewLifecycleOwner, {
            settingAdapter.updateData(it)
            onDetectScrollHeight()
        })
    }

    override fun getContainerSpacingView() = dataBinding.containerSpace

    override fun getScroller() = dataBinding.scrollView

    override fun getAddingSpace() = dataBinding.addingSpace

    override fun getContainer() = dataBinding.container

    override fun getListView() = dataBinding.rvSettings
}