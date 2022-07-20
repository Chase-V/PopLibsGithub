package com.tashevv.poplibsgithub.data

import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.retrofit.UsersListAPI
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class RetrofitUsersRepoImpl(private val api: UsersListAPI) : UsersRepo {

    override fun getUsers(): Single<List<UserEntity>> = api.getUsersList()

    override fun insertUser(userEntity: UserEntity): Completable {
        return Completable.complete()
    }

}