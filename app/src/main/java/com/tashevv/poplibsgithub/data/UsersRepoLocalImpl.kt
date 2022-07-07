package com.tashevv.poplibsgithub.data

import android.os.Handler
import android.os.Looper
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo


class UsersRepoLocalImpl : UsersRepo {

    private val localData: List<UserEntity> = mutableListOf(
        UserEntity(
            1,
            "mojombo",
            "https://avatars.githubusercontent.com/u/1?v=4",
            "https://github.com/mojombo"
        ),
        UserEntity(
            2,
            "defunkt",
            "https://avatars.githubusercontent.com/u/2?v=4",
            "https://github.com/defunkt"
        ),
        UserEntity(
            3,
            "pjhyett",
            "https://avatars.githubusercontent.com/u/3?v=4",
            "https://github.com/pjhyett"
        ),
        UserEntity(
            4,
            "wycats",
            "https://avatars.githubusercontent.com/u/4?v=4",
            "https://github.com/wycats"
        ),
        UserEntity(
            5,
            "ezmobius",
            "https://avatars.githubusercontent.com/u/5?v=4",
            "https://github.com/ezmobius"
        ),
    )

    internal fun getLocalData():List<UserEntity>{
        return localData
    }

    override fun getUsers(onSuccess: (List<UserEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        Handler(Looper.getMainLooper()).postDelayed({
            onSuccess(localData)
        }, 3_000L)
    }
}