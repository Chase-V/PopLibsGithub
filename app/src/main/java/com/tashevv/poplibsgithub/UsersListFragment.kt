package com.tashevv.poplibsgithub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tashevv.poplibsgithub.databinding.UsersListFragmentBinding

class UsersListFragment : Fragment(R.layout.users_list_fragment) {

    private var _binding: UsersListFragmentBinding? = null
    private val binding: UsersListFragmentBinding
        get() {
            return _binding!!
        }

    private val adapter = UsersListAdapter()

    companion object {
        fun newInstance(): UsersListFragment {
            val args = Bundle()

            val fragment = UsersListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UsersListFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initRecycler()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecycler() {
        binding.usersListRecyclerView.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}