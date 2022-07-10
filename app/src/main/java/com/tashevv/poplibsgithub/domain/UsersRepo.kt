package com.tashevv.poplibsgithub.domain

import io.reactivex.rxjava3.core.Single

interface UsersRepo {

//    fun addUsers():UserEntity

    fun getUsers(
        onSuccess: (List<UserEntity>) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    )

    fun getUsers():Single<List<UserEntity>>

//    fun updateUsers(position: Int)

//    fun deleteUsers(position:Int)


}