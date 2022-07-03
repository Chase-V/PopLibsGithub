package com.tashevv.poplibsgithub.ui.usersListUI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tashevv.poplibsgithub.R
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.app
import com.tashevv.poplibsgithub.databinding.UsersListFragmentBinding

class UsersListFragment : Fragment(R.layout.users_list_fragment) {

    companion object {
        fun newInstance(): UsersListFragment {
            val args = Bundle()
            val fragment = UsersListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: UsersListFragmentBinding? = null
    private val binding: UsersListFragmentBinding
        get() {
            return _binding!!
        }

    private val adapter = UsersListAdapter()
    private val repo: UsersRepo by lazy { app.usersRepo }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UsersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecycler()
        loadData()
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }





    private fun initRecycler() {
        binding.usersListRecyclerView.adapter = adapter
    }

    private fun loadData() {
        showProgressBar(true)
        repo.getUsers(
            onSuccess = {
                showProgressBar(false)
                adapter.setData(it)
            },
            onError = {
                showProgressBar(false)
                Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.usersListRecyclerView.isVisible = !isLoading
    }

}