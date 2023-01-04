package com.cxz.kotlin.baselibs.ext

import com.cxz.kotlin.baselibs.bean.BaseBean
import com.cxz.kotlin.baselibs.http.HttpStatus
import com.cxz.kotlin.baselibs.http.exception.ExceptionHandle
import com.cxz.kotlin.baselibs.http.function.RetryWithDelay
import com.cxz.kotlin.baselibs.mvp.IModel
import com.cxz.kotlin.baselibs.mvp.IView
import com.cxz.kotlin.baselibs.rx.SchedulerUtils
import com.cxz.kotlin.baselibs.utils.NetWorkUtil
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import retrofit2.HttpException

/**
 * @author chenxz
 * @date 2018/11/20
 * @desc
 */

fun <T : BaseBean> Observable<T>.ss(
    model: IModel?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit,
    onError: ((T) -> Unit)? = null
) {
    this.compose(SchedulerUtils.ioToMain())
//        .retryWhen(RetryWithDelay())
        .subscribe(object : Observer<T> {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
                if (isShowLoading) view?.showLoading()
                model?.addDisposable(d)
                if (!NetWorkUtil.isConnected()) {
                    view?.showDefaultMsg("当前网络不可用，请检查网络设置")
                    d.dispose()
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                view?.hideLoading()
                when (t.resultcode) {
                    HttpStatus.SUCCESS -> onSuccess.invoke(t)
                    else -> {
                        if (onError != null) {
                            onError.invoke(t)
                        } else {
                            if (t.message.isNotEmpty())
                                view?.showDefaultMsg(t.message)
                        }
                    }
                }
            }

            override fun onError(t: Throwable) {
                view?.hideLoading()
                if (t is HttpException && t.code() == 401) {
                    view?.logout()
                } else {
                    view?.showMsg(ExceptionHandle.handleException(t))
                }
            }
        })
}

fun <T : BaseBean> Observable<T>.sss(
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit,
    onError: ((T) -> Unit)? = null
): Disposable {
    if (isShowLoading) view?.showLoading()
    return this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe({
            if (isShowLoading) view?.showLoading()
            when (it.resultcode) {
                HttpStatus.SUCCESS -> onSuccess.invoke(it)
                else -> {
                    if (onError != null) {
                        onError.invoke(it)
                    } else {
                        if (it.message.isNotEmpty())
                            view?.showDefaultMsg(it.message)
                    }
                }
            }
        }, {
            view?.hideLoading()
            if (it is HttpException && it.code() == 401) {
                    view?.logout()
            } else {
                view?.showMsg(ExceptionHandle.handleException(it))
            }
        })
}
