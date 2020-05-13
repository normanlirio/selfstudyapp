package com.teamzmron.selfstudyapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.teamzmron.selfstudyapp.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : DaggerAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer)

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

        return when (item.itemId) {

            android.R.id.home -> {
                if(drawer.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                } else {
                    false
                }

            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navNounAdd -> {
                if (isValidDestination(R.id.nounList)) {
                    Navigation.findNavController(this,
                        R.id.nav_host_fragment
                    )
                        .navigate(R.id.nounAdd)
                }
            }
            R.id.navVerbAdd -> {
                if (isValidDestination(R.id.verbList)) {

                    Navigation.findNavController(this,
                        R.id.nav_host_fragment
                    )
                        .navigate(R.id.verbAdd)
                }
            }
            R.id.navAdjAdd -> {
                if (isValidDestination(R.id.adjList)) {
                    Navigation.findNavController(this,
                        R.id.nav_host_fragment
                    )
                        .navigate(R.id.adjAdd)
                }
            }
           else ->   Navigation.findNavController(this,
               R.id.nav_host_fragment
           )
               .navigate(R.id.homeFragment)

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
        ) || super.onSupportNavigateUp()
    }
}
