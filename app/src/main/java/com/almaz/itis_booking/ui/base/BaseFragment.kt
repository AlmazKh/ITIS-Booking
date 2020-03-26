package com.almaz.itis_booking.ui.base

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.almaz.itis_booking.R
import com.almaz.itis_booking.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

open class BaseFragment : Fragment() {
    lateinit var rootActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        rootActivity = activity as MainActivity
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                rootActivity.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun setArrowToolbarVisibility(show: Boolean) {
        rootActivity.supportActionBar?.setDisplayHomeAsUpEnabled(show)
//        rootActivity.actionBar?.setDisplayHomeAsUpEnabled(show)
//        rootActivity.actionBar?.setDisplayShowHomeEnabled(show)
    }

    fun setToolbarTitle(title: String) {
        rootActivity.tv_toolbar_title.text = title
    }

    fun setToolbarLogoVisibility(logoVisibility: Int) {
//        rootActivity.toolbar.logo = if (show) R.drawable.logo_kfu else null
        rootActivity.iv_toolbar_logo.visibility = logoVisibility
    }

    fun setToolbarAndBottomNavVisibility(toolbarVisibility: Int, bottomNavVisibility: Int) {
        rootActivity.toolbar.visibility = toolbarVisibility
        rootActivity.bottom_nav.visibility = bottomNavVisibility
    }

    fun showSnackbar(message: String){
        view?.let { it1 ->
            Snackbar.make(
                    it1,
                    message,
                    Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}