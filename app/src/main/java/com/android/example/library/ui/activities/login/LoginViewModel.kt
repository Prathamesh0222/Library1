package com.android.example.library.ui.activities.login

import android.content.Context
import android.util.Patterns
import com.android.example.library.R
import com.android.example.library.data.firebase.FirebaseUtil
import com.android.example.library.data.model.User
import com.android.example.library.ui.activities.base.BaseViewModel
import com.android.example.library.util.ObservableString
import com.android.example.library.util.Resource
import com.android.example.library.util.SharePreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseUtil: FirebaseUtil,
    private val sharedPreferenceUtil: SharePreferenceUtil,
    @ApplicationContext private val application: Context
) : BaseViewModel() {

    var observableEmail = ObservableString()
    var observablePassword = ObservableString()
    var isProfileCompleted = 0;

    /**
     * Method to validate user inputs for Login screen.
     */
    private fun validateLoginDetails(): Boolean {
        return when {
            observableEmail.trimmed.isBlank() -> {
                _status.postValue(Resource.Error("Please Enter Email"))
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(observableEmail.trimmed).matches() -> {
                _status.postValue(Resource.Error("Please Enter Valid Email Id"))
                false
            }
            observablePassword.trimmed.isBlank() -> {
                _status.postValue(Resource.Error("Please Enter Password"))
                false
            }
            observablePassword.trimmed.length < 6 -> {
                _status.postValue(Resource.Error("Password should have at least 6 characters"))
                false
            }
            else -> true
        }
    }

    /**
     * Method to login the registered user.
     */
    fun loginRegisteredUser() {
        if (validateLoginDetails()) {
            _status.postValue(Resource.Loading())
            firebaseUtil.firebaseAuth.signInWithEmailAndPassword(
                observableEmail.trimmed,
                observablePassword.trimmed
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUtil.getUserDetails { user ->
                        saveUserDetails(user)
                        _status.postValue(Resource.Success("Login Successful"))
                    }
                } else {
                    _status.postValue(Resource.Error(task.exception?.message.toString()))
                }
            }
        }
    }

    /**
     * Method to save user data into shared preferences.
     */
    private fun saveUserDetails(user: User) {
        sharedPreferenceUtil.clearData() // clear the outdated data

        sharedPreferenceUtil.setString(
            application.getString(R.string.prefUserId),
            user.uid
        )

        sharedPreferenceUtil.setString(
            application.getString(R.string.prefProfileImagePath),
            user.image
        )

        sharedPreferenceUtil.setString(
            application.getString(R.string.prefFullName),
            "${user.firstName} ${user.lastName}"
        )

        sharedPreferenceUtil.setString(
            application.getString(R.string.prefFirstName),
            user.firstName
        )
        sharedPreferenceUtil.setString(
            application.getString(R.string.prefLastName),
            user.lastName
        )
        sharedPreferenceUtil.setString(
            application.getString(R.string.prefEmail),
            user.email
        )

        sharedPreferenceUtil.setString(
            application.getString(R.string.prefMobile),
            user.mobile
        )

        sharedPreferenceUtil.setString(
            application.getString(R.string.prefGender),
            user.gender
        )

        sharedPreferenceUtil.setInteger(
            R.string.prefIsProfileCompleted,
            user.profileCompleted
        )

        isProfileCompleted = user.profileCompleted
    }

}