package com.tashevv.poplibsgithub.data

import android.os.Handler
import android.os.Looper
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.retrofit.UsersListRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersRepoRetrofitImpl : UsersRepo {

    private var data = listOf<UserEntity>()
    private val retrofitImpl = UsersListRetrofitImpl()
    private fun getRemoteData() = retrofitImpl.getRetrofitImpl().getUsersList().enqueue(callback)

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
            throw Throwable("Ответ от сервера не пришел")
        }

    }

    override fun getUsers(onSuccess: (List<UserEntity>) -> Unit, onError: ((Throwable) -> Unit)?) {
        getRemoteData()

        Handler(Looper.getMainLooper()).postDelayed({
            onSuccess(data)
        }, 3_000L)
    }


}