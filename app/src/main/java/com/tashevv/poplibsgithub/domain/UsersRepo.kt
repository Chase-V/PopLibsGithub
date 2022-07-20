package com.tashevv.poplibsgithub.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface UsersRepo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity): Completable


    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    fun getUsers(): Single<List<UserEntity>>

}