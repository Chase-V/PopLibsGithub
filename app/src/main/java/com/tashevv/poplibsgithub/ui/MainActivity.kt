package com.tashevv.poplibsgithub.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.tashevv.poplibsgithub.app
import com.tashevv.poplibsgithub.databinding.ActivityMainBinding
import com.tashevv.poplibsgithub.domain.UserEntity
import com.tashevv.poplibsgithub.domain.UsersRepo
import com.tashevv.poplibsgithub.domain.viewModel.UsersListViewModel
import com.tashevv.poplibsgithub.ui.userCardDialog.UserCardDialogFragment
import com.tashevv.poplibsgithub.ui.usersListUI.RecyclerItemClickListener
import com.tashevv.poplibsgithub.ui.usersListUI.UsersListAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val adapter = UsersListAdapter()

    @Inject
    lateinit var usersRepo: UsersRepo
    private val viewModel: UsersListViewModel by viewModels()


    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app.appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


    private fun initViewModel() {
        compositeDisposable.addAll(
            viewModel.progressObservable.subscribe { showProgressBar(it) },
            viewModel.errorObservable.subscribe { showError(it) },
            viewModel.usersObservable.subscribe { showUsers(it) },
            binding.usersListRefreshButton.createButtonClickObservable()
                .subscribe { viewModel.onRefresh() }
        )
    }


    private fun initViews() {
        initRecycler()
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