package com.tashevv.poplibsgithub.domain

interface UsersRepo {

//    fun addUsers():UserEntity

    fun getUsers(
        onSuccess:(List<UserEntity>) -> Unit,
        onError:((Throwable)->Unit)? = null
    )

//    fun updateUsers(position: Int)

//    fun deleteUsers(position:Int)


}