package com.tashevv.poplibsgithub

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UsersListAdapter : RecyclerView.Adapter<UsersListViewHolder>() {

    private val data = mutableListOf<UserEntity>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder =
        UsersListViewHolder(parent)

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) = holder.bind(getItem(position))

    override fun getItemCount(): Int = data.size

    private fun getItem(pos: Int) = data[pos]

    fun setData(users: List<UserEntity>) {
        data.clear()
        data.addAll(users)
        notifyDataSetChanged()
    }
}