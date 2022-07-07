/* На память о возне с фрагментом, может пригодится в будущем
package com.tashevv.poplibsgithub.ui.usersListUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tashevv.poplibsgithub.R
import com.tashevv.poplibsgithub.app
import com.tashevv.poplibsgithub.databinding.UsersListFragmentBinding
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersListFragmentPresenter

class UsersListFragment : Fragment(R.layout.users_list_fragment), UsersContract.View {

    companion object {
        fun newInstance(): UsersListFragment {
            val args = Bundle()
            val fragment = UsersListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val presenter: UsersContract.Presenter by lazy { extractPresenter() }

    private var _binding: UsersListFragmentBinding? = null
    private val binding: UsersListFragmentBinding
        get() {
            return _binding!!
        }

    private val adapter = UsersListAdapter()
//    private val repo: UsersRepo by lazy { app.usersRepo }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        presenter.attach(this)
        _binding = UsersListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        presenter.detach()
    }

    private fun extractPresenter(): UsersContract.Presenter {
        return requireActivity().lastCustomNonConfigurationInstance as? UsersContract.Presenter
            ?: UsersListFragmentPresenter(app.usersRepo)
    }

    private fun initViews() {
        binding.usersListRefreshButton.setOnClickListener {
            presenter.onRefresh()
        }
        initRecycler()
    }

    private fun initRecycler() {
        binding.usersListRecyclerView.adapter = adapter
    }

    override fun showUsers(users: List<UserEntity>) {
        adapter.setData(users)
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.usersListRecyclerView.isVisible = !isLoading
    }

}*/
