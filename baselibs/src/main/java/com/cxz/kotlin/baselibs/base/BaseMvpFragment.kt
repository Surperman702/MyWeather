package com.cxz.kotlin.baselibs.base

import android.view.View
import androidx.annotation.LayoutRes
import com.cxz.kotlin.baselibs.ext.showToast
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView
import com.cxz.kotlin.baselibs.utils.LogoutUtil
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * @author chenxz
 * @date 2018/11/19
 * @desc BaseMvpFragment
 */
abstract class BaseMvpFragment<in V : IView, P : IPresenter<V>>(@LayoutRes layoutId: Int) :
    BaseFragment(layoutId), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    private var loading: LoadingPopupView? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun showLoading() {
        if (loading == null) {
            loading = XPopup.Builder(context).asLoading()
        }
        loading?.let {
            if (!it.isShow) {
                it.show()
            }
        }
    }

    override fun hideLoading() {
        loading?.let {
            if (it.isShow) {
                it.dismiss()
            }
        }
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun logout() {
        LogoutUtil.logout()
    }

}