/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kidscrafts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.facebook.CallbackManager
import com.kidscrafts.adapters.GardenPlantingAdapter
import com.kidscrafts.databinding.FragmentLoginBinding
import com.kidscrafts.utilities.InjectorUtils
import com.kidscrafts.viewmodels.GardenPlantingListViewModel

class LoginFragment : Fragment() {

    var callbackManager: CallbackManager? = null
    lateinit var facebook_login: Button
    lateinit var name: TextView
    lateinit var email: TextView
    lateinit var dob: TextView
    lateinit var gender: TextView
    lateinit var profile: ImageView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter(binding.root.context)
//        binding.gardenList.adapter = adapter


        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentLoginBinding) {
        val factory = InjectorUtils.provideGardenPlantingListViewModelFactory(requireContext())
        val viewModel = ViewModelProviders.of(this, factory)
                .get(GardenPlantingListViewModel::class.java)

        viewModel.gardenPlantings.observe(viewLifecycleOwner, Observer { plantings ->
            binding.hasPlantings = (plantings != null && plantings.isNotEmpty())
        })

        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner, Observer { result ->
            if (result != null && result.isNotEmpty())
                adapter.submitList(result)
        })

        viewModel.styleTextView(binding.tvSignup, getString(R.string.do_not_have_account), getString(R.string.signup))
        callbackManager = CallbackManager.Factory.create();
//        facebookSignInButton.setReadPermissions("email")
//        // Callback registration
//        facebookSignInButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
//            override fun onSuccess(loginResult: LoginResult) {
//                // App code
//                handleFacebookAccessToken(loginResult.accessToken);
//            }
//
//            override fun onCancel() {
//                // App code
//            }
//
//            override fun onError(exception: FacebookException) {
//                // App code
//            }
//        })
    }
}
