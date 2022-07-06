package com.tashevv.poplibsgithub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.tashevv.poplibsgithub.R
import com.tashevv.poplibsgithub.ui.usersListUI.UsersListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState.let {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.mainActivityContainer, UsersListFragment.newInstance())
            }

        }
    }


}