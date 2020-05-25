package com.almaz.itis_booking.ui.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.almaz.itis_booking.ui.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

open class BaseFragment : Fragment() {
    lateinit var rootActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        rootActivity = activity as MainActivity
    }

    fun setArrowToolbarVisibility(visibility: Int) {
        rootActivity.btn_back.visibility = visibility
        if (visibility == View.VISIBLE) {
            rootActivity.btn_back.setOnClickListener {
                rootActivity.navController.popBackStack()
            }
        }
    }

    fun setToolbarTitle(title: String) {
        rootActivity.tv_toolbar_title.text = title
    }

    fun setToolbarElevation(elevation: Float) {
        rootActivity.toolbar.elevation = elevation
    }

    fun setToolbarLogoVisibility(logoVisibility: Int) {
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