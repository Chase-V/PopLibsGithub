package com.tashevv.poplibsgithub.ui.usersListUI

import androidx.lifecycle.LiveData
import com.tashevv.poplibsgithub.domain.UserEntity

interface UsersContract {


    interface ViewModel {
        val usersLiveData: LiveData<List<UserEntity>>
        val errorLiveData: LiveData<Throwable>
        val progressBarLiveData: LiveData<Boolean>
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