package com.almaz.itis_booking.ui.profile

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.core.model.User
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.ui.main.MainViewModel
import com.almaz.itis_booking.utils.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProfileViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent
            .profileComponent()
            .withActivity(activity as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this, this.viewModelFactory)
            .get(ProfileViewModel::class.java)
        mainViewModel = ViewModelProvider(rootActivity, this.viewModelFactory)
            .get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )

        btn_priority_explanation.setOnClickListener {
            rootActivity.navController.navigate(R.id.dialogHelpProfilePriority)
        }
        viewModel.getUserInfo()
        observeUserInfoLiveData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.with_logout_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_logout -> {
                mainViewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeUserInfoLiveData() =
        viewModel.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.data != null) {
                    setUserInfo(it.data)
                }
                if (it.error != null) {
                    showSnackbar(getString(R.string.snackbar_error_message))
                }
            }
        })

    private fun setUserInfo(user: User) {
        tv_user_name_profile.text = user.name
        tv_group_number.text = user.groupNumber
        tv_institute_value.text = user.institute
        tv_group_value.text = user.groupNumber
        tv_priority_value.text = user.priority
        tv_email_value.text = user.email

        Glide.with(this)
            .load(user.photo)
            .centerCrop()
            .circleCrop()
            .into(iv_user_avatar_profile)
    }
}
