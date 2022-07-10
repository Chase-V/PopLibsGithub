package com.tashevv.poplibsgithub.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tashevv.poplibsgithub.ui.usersListUI.UsersContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class UsersListViewModel(
    private val usersRepo: UsersRepo
) : UsersContract.ViewModel {

    override val usersLiveData: Observable<List<UserEntity>> = BehaviorSubject.create()
    override val errorLiveData: Observable<Throwable> = BehaviorSubject.create()
    override val progressBarLiveData: Observable<Boolean> = BehaviorSubject.create()


    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        progressBarLiveData.toSubject().onNext(true)

/* без RxJava
        usersRepo.getUsers(
            onSuccess = {
                usersLiveData.toMutable().postValue(it)
                progressBarLiveData.toMutable().postValue(false)
            },
            onError = {
                progressBarLiveData.toMutable().postValue(false)
                errorLiveData.toMutable().postValue(it)
            }
        )*/

//        C RxJava
        usersRepo
            .getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    usersLiveData.toSubject().onNext(it)
                    progressBarLiveData.toSubject().onNext(false)
                },
                onError = {
                    progressBarLiveData.toSubject().onNext(false)
                    errorLiveData.toSubject().onNext(it)
                }
            )
    }

    private fun <T> LiveData<T>.toMutable(): MutableLiveData<T> {
        return this as? MutableLiveData<T>
            ?: throw IllegalStateException("This is not a LiveData")
    }

    private fun <T : Any> Observable<T>.toSubject(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("This is not a Observable")
    }
}