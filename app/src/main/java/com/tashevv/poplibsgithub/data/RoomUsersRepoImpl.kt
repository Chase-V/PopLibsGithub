package com.tashevv.poplibsgithub.data

import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.room.RoomUsersListDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


class RoomUsersRepoImpl(private val dbInstance: RoomUsersListDatabase) : UsersRepo {

    override fun insertUser(userEntity: UserEntity): Completable {
        dbInstance.usersListDao().insertUser(userEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
        return Completable.complete()
    }

    override fun getUsers(): Single<List<UserEntity>> =
        dbInstance.usersListDao().getUsers().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}