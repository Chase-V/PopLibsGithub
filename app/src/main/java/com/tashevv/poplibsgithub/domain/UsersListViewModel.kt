package com.tashevv.poplibsgithub.domain

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

    private fun <T : Any> Observable<T>.toSubject(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("This is not a Observable")
    }
}


//    private fun <T> LiveData<T>.toMutable(): MutableLiveData<T> {
//        return this as? MutableLiveData<T>
//            ?: throw IllegalStateException("This is not a LiveData")
//    }

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