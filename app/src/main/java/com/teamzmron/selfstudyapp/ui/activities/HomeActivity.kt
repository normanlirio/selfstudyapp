package com.teamzmron.selfstudyapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.teamzmron.selfstudyapp.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        var navController: NavController =
            Navigation.findNavController(this,
                R.id.nav_host_fragment
            )
        NavigationUI.setupActionBarWithNavController(this, navController, drawer)
        NavigationUI.setupWithNavController(navView, navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {
                return if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                    true;
                } else{
                    false;
                }
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navNounHomeList -> {
                if (isValidDestination(R.id.nounList)) {
                    Navigation.findNavController(this,
                        R.id.nav_host_fragment
                    )
                        .navigate(R.id.nounList)
                }
            }
            R.id.navVerbHomeList -> {
                if (isValidDestination(R.id.verbList)) {

                    Navigation.findNavController(this,
                        R.id.nav_host_fragment
                    )
                        .navigate(R.id.verbList)
                }
            }
            R.id.navAdjHomeList -> {
                if (isValidDestination(R.id.adjList)) {
                    Navigation.findNavController(this,
                        R.id.nav_host_fragment
                    )
                        .navigate(R.id.adjList)
                }
            }

        }

        item.isChecked = true
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun isValidDestination(destination: Int): Boolean =
        destination != Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
            .currentDestination!!.id

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this,
                R.id.nav_host_fragment
            ),
            drawer
        )
    }
}
