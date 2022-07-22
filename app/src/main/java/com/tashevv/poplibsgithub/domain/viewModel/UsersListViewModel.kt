package com.tashevv.poplibsgithub.domain.viewModel

import androidx.lifecycle.ViewModel
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.ui.usersListUI.UsersContract
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val usersRepo: UsersRepo
) : UsersContract.ViewModel, ViewModel() {

    override val usersObservable: Observable<List<UserEntity>> = BehaviorSubject.create()
    override val errorObservable: Observable<Throwable> = BehaviorSubject.create()
    override val progressObservable: Observable<Boolean> = BehaviorSubject.create()

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        progressObservable.toSubject().onNext(true)
        usersRepo
            .getUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    usersObservable.toSubject().onNext(it)
                    progressObservable.toSubject().onNext(false)
                },
                onError = {
                    progressObservable.toSubject().onNext(false)
                    errorObservable.toSubject().onNext(it)
                }
            )

    }

    private fun <T : Any> Observable<T>.toSubject(): Subject<T> {
        return this as? Subject<T>
            ?: throw IllegalStateException("This is not a Observable")
    }
}