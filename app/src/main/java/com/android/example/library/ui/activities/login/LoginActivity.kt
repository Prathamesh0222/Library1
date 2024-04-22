package com.android.example.library.ui.activities.login

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.example.library.R
import com.android.example.library.ui.activities.base.BaseActivity
import com.android.example.library.ui.activities.forgot_password.ForgotPasswordActivity
import com.android.example.library.ui.activities.registration.RegistrationActivity
import com.android.example.library.util.Resource
import com.android.example.library.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var mBinding: ActivityLoginBinding
    private val mViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.viewModel = mViewModel

        mBinding.tvDontHaveAnAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
        }

        mBinding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
        }

        observeLiveEvents()
    }

    /**
     * Observe changes in the LiveData.
     */
    private fun observeLiveEvents() {
        mViewModel.status.observe(this) { status ->
            when (status) {
                is Resource.Success<*> -> {
                    showSnackBar(mBinding.root, status.data ?: "Success", false)
                    hideProgressbar()
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java).apply {
                        putExtra(
                            getString(R.string.prefIsProfileCompleted),
                            mViewModel.isProfileCompleted
                        )
                    })
                    finish()
                }

                is Resource.Error<*> -> {
                    showSnackBar(
                        mBinding.root,
                        status.message ?: "An unknown error occurred.",
                        true
                    )
                    hideProgressbar()
                }

                is Resource.Loading<*> -> {
                    showProgressbar()
                }
            }
        }
    }
}