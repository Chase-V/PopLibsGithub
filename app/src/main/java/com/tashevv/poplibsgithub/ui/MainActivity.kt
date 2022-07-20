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
import com.tashevv.poplibsgithub.databinding.ActivityMainBinding
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersListViewModel
import com.tashevv.poplibsgithub.ui.userCardDialog.UserCardDialogFragment
import com.tashevv.poplibsgithub.ui.usersListUI.RecyclerItemClickListener
import com.tashevv.poplibsgithub.ui.usersListUI.UsersListAdapter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: UsersListViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private val adapter = UsersListAdapter()
    private val viewModelDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelDisposable.dispose()
    }


    private fun initViewModel() {
        viewModelDisposable.addAll(
            viewModel.progressObservable.subscribe { showProgressBar(it) },
            viewModel.errorObservable.subscribe { showError(it) },
            viewModel.usersObservable.subscribe { showUsers(it) },
        )
    }


    private fun initViews() {
        initRecycler()

        binding.usersListRefreshButton.createButtonClickObservable()
            .subscribe { viewModel.onRefresh() }

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
}