package com.almaz.itis_booking.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.almaz.itis_booking.App
import com.almaz.itis_booking.R
import com.almaz.itis_booking.core.model.User
import com.almaz.itis_booking.ui.base.BaseFragment
import com.almaz.itis_booking.utils.ViewModelFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProfileViewModel

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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarAndBottomNavVisibility(
            toolbarVisibility = View.VISIBLE,
            bottomNavVisibility = View.VISIBLE
        )

        viewModel.getUserInfo()
        observeUserInfoLiveData()
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

//        val transformation = RoundedCornersTransformation(20, 1)
//
//        val requestOptions = RequestOptions()
//            .centerCrop()
//            .transforms(transformation)
//
//        val thumbnail = Glide.with(this)
//            .load(R.drawable.)
//            .apply(requestOptions)

        Glide.with(this)
            .load(user.photo)
            .centerCrop()
            .circleCrop()
            .into(iv_user_avatar_profile)
    }
}