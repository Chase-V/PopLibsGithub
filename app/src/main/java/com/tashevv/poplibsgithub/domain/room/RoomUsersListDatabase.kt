package com.tashevv.poplibsgithub.domain.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo

@Database(entities = [UserEntity::class], version = DB_VERSION)
abstract class RoomUsersListDatabase : RoomDatabase() {

    abstract fun usersListDao(): UsersRepo

    companion object {

        @Volatile
        private var databaseInstance: RoomUsersListDatabase? = null

        fun getDatabaseInstance(mContext: Context): RoomUsersListDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabaseInstance(mContext).also {
                    databaseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, RoomUsersListDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}

const val DB_VERSION = 1

const val DB_NAME = "UsersListDatabase.db"