package com.tashevv.poplibsgithub.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity (tableName = UserEntity.TABLE_NAME)
data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    @SerializedName("id") val id: Long,

    @ColumnInfo(name = LOGIN)
    @SerializedName("login") val login: String,

    @ColumnInfo(name = AVATAR_URL)
    @SerializedName("avatar_url") val avatarUrl: String,

    @ColumnInfo(name = HTML_URL)
    @SerializedName("html_url") val htmlUrl: String
){
    companion object{
        const val TABLE_NAME="list_of_users"
        const val ID = "id"
        const val LOGIN ="login"
        const val AVATAR_URL = "avatar_url"
        const val HTML_URL = "html_url"
    }
}