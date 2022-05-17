package com.example.cryptocurrency.ui.base

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    lateinit var mBinding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var navHostFragment:NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.findNavController()
        setSupportActionBar(mBinding.toolBar)

        val actionToggle = ActionBarDrawerToggle(
            this,
            mBinding.llDrawer,
            mBinding.toolBar,
            R.string.open_drawer,R.string.close_drawer
        )

        mBinding.llDrawer.addDrawerListener(actionToggle)
        actionToggle.isDrawerIndicatorEnabled = true
        actionToggle.setToolbarNavigationClickListener {
            mBinding.llDrawer.openDrawer(GravityCompat.START)
        }
        actionToggle.syncState()

        mBinding.bottomNavView.setupWithNavController(navController)
    }
}