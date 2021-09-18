package com.jason.app.base.viewmodel

import androidx.lifecycle.*
import com.jason.app.extensions.getDefault
import com.jason.app.extensions.logError
import com.jason.app.utils.observer.DisposableBag
import io.reactivex.observers.DisposableObserver
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val TAG = this::class.simpleName

    protected val _isLoadingObs = MutableLiveData<Boolean>()
    val isLoadingObs: LiveData<Boolean>
        get() = _isLoadingObs


    protected val _errorObs = MutableLiveData<Throwable>()
    val errorObs: LiveData<Throwable>
        get() = _errorObs

    protected val compositeDisposableBag: DisposableBag by lazy {
        DisposableBag()
    }

    fun setLoading(isLoading: Boolean) {
        TAG?.logError("$isLoading")
        _isLoadingObs.postValue(isLoading)
    }

    fun setError(error: Throwable? = null) {
        TAG?.logError(error?.message.getDefault())
        _errorObs.postValue(error)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        compositeDisposableBag.onStop()
    }

    /**
     * Process Retrofit callback with auto show/hide loading indicator and error notification
     */
    abstract inner class FullCallbackWrapper<T> constructor(private val isLoading: Boolean) : DisposableObserver<T>() {
        protected abstract fun onResponse(response: T)
        override fun onStart() {
            setLoading(isLoading)
        }

        override fun onNext(t: T) {
            onResponse(t)
            setLoading(false)
        }

        override fun onComplete() {
            setLoading(false)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            setLoading(false)
            setError(error = e)
        }
    }
}