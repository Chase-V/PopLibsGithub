package com.tashevv.poplibsgithub.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.tashevv.poplibsgithub.app
import com.tashevv.poplibsgithub.databinding.ActivityMainBinding
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersListViewModel
import com.tashevv.poplibsgithub.ui.userCardDialog.UserCardDialogFragment
import com.tashevv.poplibsgithub.ui.usersListUI.RecyclerItemClickListener
import com.tashevv.poplibsgithub.ui.usersListUI.UsersContract
import com.tashevv.poplibsgithub.ui.usersListUI.UsersListAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UsersContract.ViewModel

    private val adapter = UsersListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }


    private fun initViewModel() {
        viewModel = extractViewModel()
        viewModel.progressBarLiveData.observe(this) { showProgressBar(it) }
        viewModel.errorLiveData.observe(this) { showError(it) }
        viewModel.usersLiveData.observe(this) { showUsers(it) }
    }


    private fun initViews() {
        initRecycler()
        binding.usersListRefreshButton.setOnClickListener {
            viewModel.onRefresh()
        }
        addOnUserClickListener(binding.usersListRecyclerView)
    }


    private fun initRecycler() {
        binding.usersListRecyclerView.adapter = adapter
    }


    private fun showUsers(users: List<UserEntity>) {
        adapter.setData(users)
    }


    private fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }


    private fun showProgressBar(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.usersListRecyclerView.isVisible = !isLoading
    }


    private fun addOnUserClickListener(recycler: RecyclerView) {
        recycler.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recycler,
                object : RecyclerItemClickListener.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            add(UserCardDialogFragment(adapter.getItem(position)), "userCard")
                        }
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(adapter.getItem(position).htmlUrl)
                        }
                        )
                    }
                }
            )
        )
    }


    private fun extractViewModel(): UsersContract.ViewModel {
        @Suppress("DEPRECATION")
        return lastCustomNonConfigurationInstance as? UsersContract.ViewModel
            ?: UsersListViewModel(app.usersRepo)
    }


    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): UsersContract.ViewModel {
        return viewModel
    }
}


/* На память про MVP
class MainActivity : AppCompatActivity(), UsersContract.Presenter {

    private lateinit var binding: ActivityMainBinding

    private val adapter = UsersListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        presenter.attach(this)

        setContentView(binding.root)

        initViews()
    }

    private fun addOnUserClickListener(recycler: RecyclerView) {
        recycler.addOnItemTouchListener(
            RecyclerItemClickListenr(
                this,
                recycler,
                object : RecyclerItemClickListenr.OnItemClickListener {

                    override fun onItemClick(view: View, position: Int) {
                        supportFragmentManager.commit{
                            setReorderingAllowed(true)
                            add(UserCardDialogFragment(adapter.getItem(position)),"userCard")
                        }
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(adapter.getItem(position).htmlUrl)
                        })
                    }
                })
        )
    }

    @Suppress("DEPRECATION")
    private fun extractPresenter(): UsersContract.Presenter {
        return lastCustomNonConfigurationInstance as? UsersContract.Presenter
            ?: UsersListPresenter(app.usersRepo)
    }

    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): UsersContract.Presenter {
        return presenter
    }

    private fun initViews() {
        binding.usersListRefreshButton.setOnClickListener {
            presenter.onRefresh()
        }
        initRecycler()
        val recyclerV = binding.usersListRecyclerView
        addOnUserClickListener(recyclerV)
    }

    private fun initRecycler() {
        binding.usersListRecyclerView.adapter = adapter
    }

    override fun showUsers(users: List<UserEntity>) {
        adapter.setData(users)
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.usersListRecyclerView.isVisible = !isLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }


}*/
