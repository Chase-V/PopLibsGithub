package com.tashevv.poplibsgithub.ui.usersListUI

import com.tashevv.poplibsgithub.domain.UserEntity

interface UsersContract {

    interface View {
        fun showUsers(users: List<UserEntity>)
        fun showError(throwable: Throwable)
        fun showProgressBar(isLoading: Boolean)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun onRefresh()
    }
}