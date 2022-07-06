package com.tashevv.poplibsgithub.domain

import com.tashevv.poplibsgithub.ui.usersListUI.UsersContract

class UsersListPresenter(private val usersRepo: UsersRepo) : UsersContract.Presenter {

    private var view: UsersContract.View? = null
    private var usersList: List<UserEntity>? = null
    private var isLoading: Boolean = false

    override fun attach(view: UsersContract.View) {
        this.view = view
        view.showProgressBar(isLoading)
        usersList?.let { view.showUsers(it) }
    }

    override fun detach() {
        view = null
    }

    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        view?.showProgressBar(true)
        isLoading = true
        usersRepo.getUsers(
            onSuccess = {
                view?.showProgressBar(false)
                view?.showUsers(it)
                usersList = it
                isLoading = false
            },
            onError = {
                view?.showProgressBar(false)
                view?.showError(it)
                isLoading = false
            }
        )
    }
}