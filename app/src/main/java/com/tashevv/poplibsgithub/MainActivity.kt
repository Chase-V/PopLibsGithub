package com.tashevv.poplibsgithub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

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