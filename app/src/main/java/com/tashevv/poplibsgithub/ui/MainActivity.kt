package com.tashevv.poplibsgithub.ui

import android.content.Context
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
import com.tashevv.poplibsgithub.domain.UsersListPresenter
import com.tashevv.poplibsgithub.ui.usersListUI.RecyclerItemClickListenr
import com.tashevv.poplibsgithub.ui.usersListUI.UserCardDialogFragment
import com.tashevv.poplibsgithub.ui.usersListUI.UsersContract
import com.tashevv.poplibsgithub.ui.usersListUI.UsersListAdapter

class MainActivity : AppCompatActivity(), UsersContract.View {

    private val presenter: UsersContract.Presenter by lazy { extractPresenter() }

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


}