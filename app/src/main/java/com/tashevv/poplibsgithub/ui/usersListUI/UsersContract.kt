package com.tashevv.poplibsgithub.ui.usersListUI

import com.tashevv.poplibsgithub.domain.UserEntity
import io.reactivex.rxjava3.core.Observable

interface UsersContract {


    interface ViewModel {
        val usersLiveData: Observable<List<UserEntity>>
        val errorLiveData: Observable<Throwable>
        val progressBarLiveData: Observable<Boolean>
        fun onRefresh()
    }


/*    На память про MVP
    interface View {
        fun showUsers(users: List<UserEntity>)
        fun showError(throwable: Throwable)
        fun showProgressBar(isLoading: Boolean)
    }


    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun onRefresh()
    }*/
}