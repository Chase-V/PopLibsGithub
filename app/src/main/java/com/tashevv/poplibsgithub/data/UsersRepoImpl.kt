package com.tashevv.poplibsgithub.data

import android.os.Handler
import android.os.Looper
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.retrofit.UsersListRetrofitImpl
import com.tashevv.poplibsgithub.domain.UsersRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UsersRepoImpl : UsersRepo {

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

    private var data: List<UserEntity> = localData

    private val retrofitImpl = UsersListRetrofitImpl()

    //    TODO remote data source
    private fun remoteData() = retrofitImpl.getRetrofitImpl().getUsersList().enqueue(callback)

    private val callback = object : Callback<List<UserEntity>> {

        override fun onResponse(
            call: Call<List<UserEntity>>,
            response: Response<List<UserEntity>>
        ) {
            if (response.isSuccessful && response.body() != null) {
                data = response.body()!!
            } else Throwable(response.errorBody().toString())
        }

        override fun onFailure(call: Call<List<UserEntity>>, t: Throwable) {
            data = localData
        }

    }

    override fun getUsers(onSuccess: (List<UserEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        remoteData()

        Handler(Looper.getMainLooper()).postDelayed({
            onSuccess(data)
        }, 3_000L)
    }
}