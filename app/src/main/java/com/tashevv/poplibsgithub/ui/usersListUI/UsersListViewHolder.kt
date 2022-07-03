package com.tashevv.poplibsgithub.ui.usersListUI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.tashevv.poplibsgithub.R
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.databinding.ItemRecyclerViewUsersListFragmentBinding

class UsersListViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_recycler_view_users_list_fragment, parent, false)
) {

    private val binding = ItemRecyclerViewUsersListFragmentBinding.bind(itemView)

    fun bind(userEntity: UserEntity) {
        binding.userCardLoginTextView.text = userEntity.login
        binding.userCardAvatarImageView.load(userEntity.avatarUrl) {
            crossfade(true)
            transformations(RoundedCornersTransformation(20f))
        }
    }

}