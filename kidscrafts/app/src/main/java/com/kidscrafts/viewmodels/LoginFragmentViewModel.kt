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
    private val TAG = LoginFragmentViewModel::class.java.getSimpleName()

    private var user = MutableLiveData<User>()

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var callbackManager: CallbackManager
    lateinit var facebookCallback: FacebookCallback<LoginResult>


    init {
        firebaseAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()
        facebookCallback = object : FacebookCallback<LoginResult> {
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
    }

    fun getUser(): LiveData<User> = user

    fun getFBCallbackManager() = callbackManager

    fun getFBCallback() = facebookCallback

    private fun signup(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:" + token)
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth!!.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.value = User(firebaseAuth.currentUser?.displayName!!, firebaseAuth.currentUser?.uid!!)
                    }
                }
    }
}
