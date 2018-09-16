package com.kidscrafts.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.kidscrafts.dto.User


/**
 * The ViewModel for [LoginFragment].
 */
class LoginFragmentViewModel : ViewModel() {

    private var user = MutableLiveData<User>()

    private var callbackManager: CallbackManager? = null
    private var firebaseAuth: FirebaseAuth? = null

    private val TAG = LoginFragmentViewModel::class.java.getSimpleName()

    init {
        callbackManager = CallbackManager.Factory.create()
    }

    fun getUser() : LiveData<User> = user

    fun getFBCallbackManager() = callbackManager

    fun getFBCallback() = object : FacebookCallback<LoginResult> {
        override fun onSuccess(loginResult: LoginResult) {
            // App code
            signup(loginResult.accessToken)
        }

        override fun onCancel() {
            // App code
        }

        override fun onError(exception: FacebookException) {
            // App code
        }
    }

    private fun signup(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + token)
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var tempUser : User = User(firebaseAuth!!.currentUser!!.displayName!! , firebaseAuth!!.currentUser!!.uid!!)
                        user.value = tempUser
                    }
                    // else {
//                        user = null
//                    }
                }
    }
}
