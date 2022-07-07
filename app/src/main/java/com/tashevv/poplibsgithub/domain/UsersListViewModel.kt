package com.tashevv.poplibsgithub.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tashevv.poplibsgithub.ui.usersListUI.UsersContract

class UsersListViewModel(
    private val usersRepo: UsersRepo
) : UsersContract.ViewModel {

    override val usersLiveData: LiveData<List<UserEntity>> = MutableLiveData()
    override val errorLiveData: LiveData<Throwable> = MutableLiveData()
    override val progressBarLiveData: LiveData<Boolean> = MutableLiveData()


    override fun onRefresh() {
        loadData()
    }

    private fun loadData() {
        progressBarLiveData.toMutable().postValue(true)

        usersRepo.getUsers(
            onSuccess = {
                usersLiveData.toMutable().postValue(it)
                progressBarLiveData.toMutable().postValue(false)
            },
            onError = {
                progressBarLiveData.toMutable().postValue(false)
                errorLiveData.toMutable().postValue(it)
            }
        )
    }
}

private fun <T> LiveData<T>.toMutable(): MutableLiveData<T> {
    return this as? MutableLiveData<T>
        ?: throw IllegalStateException("This is not a LiveData")
}