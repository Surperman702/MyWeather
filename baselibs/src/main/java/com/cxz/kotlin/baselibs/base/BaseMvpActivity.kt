package com.cxz.kotlin.baselibs.base

import com.cxz.kotlin.baselibs.ext.showToast
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView
import com.cxz.kotlin.baselibs.utils.LogoutUtil
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * @author chenxz
 * @date 2018/11/19
 * @desc BaseMvpActivity
 */
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    private var loading: LoadingPopupView? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun showLoading() {
        if (loading == null) {
            loading = XPopup.Builder(this).asLoading()
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