package com.tashevv.poplibsgithub.data

import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class UsersRepoImpl : UsersRepo {

    private val retrofit: RetrofitUsersRepoImpl = RetrofitUsersRepoImpl()
    private val room: RoomUsersRepoImpl = RoomUsersRepoImpl()

    override fun insertUser(userEntity: UserEntity): Completable = Completable.complete()

    override fun getUsers(): Single<List<UserEntity>> =
        retrofit.getRetrofitImpl().getUsersList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { it -> it.forEach { room.insertUser(it) } }
            .onErrorResumeNext { room.getUsers() }


}