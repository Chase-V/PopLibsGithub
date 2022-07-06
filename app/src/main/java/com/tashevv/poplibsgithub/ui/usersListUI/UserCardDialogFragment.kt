package com.tashevv.poplibsgithub.ui.usersListUI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.load
import coil.transform.RoundedCornersTransformation
import com.tashevv.poplibsgithub.R
import com.tashevv.poplibsgithub.databinding.UserCardDialogBinding
import com.tashevv.poplibsgithub.domain.UserEntity

class UserCardDialogFragment(private val user: UserEntity) :
    DialogFragment(R.layout.user_card_dialog) {

    private var _binding: UserCardDialogBinding? = null
    private val binding: UserCardDialogBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserCardDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

    }

    private fun initViews() {
        binding.userCardDialogAvatarImageView.load(user.avatarUrl){
            crossfade(300)
            placeholder(requireContext().getDrawable(R.drawable.github_user_icon))
            transformations(RoundedCornersTransformation(20f))
        }

        binding.userCardDialogLoginTextView.text = user.login
        binding.userCardDialogHTMLUrlTextView.text = user.htmlUrl

        binding.userCardDialogHTMLUrlTextView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(user.htmlUrl)
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}