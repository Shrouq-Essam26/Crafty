package com.kidscrafts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.kidscrafts.databinding.FragmentLoginBinding
import com.kidscrafts.utilities.InjectorUtils
import com.kidscrafts.viewmodels.LoginFragmentViewModel


class LoginFragment : Fragment() {
    private val TAG = LoginFragment::class.java.getSimpleName()

    lateinit var viewModel: LoginFragmentViewModel;

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        initializeViewModel()
        initializeViews(binding)
        return binding.root
    }

    private fun initializeViewModel() {
        val factory = InjectorUtils.provideLoginFragmentViewModelFactory(requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(LoginFragmentViewModel::class.java)
    }

    private fun initializeViews(binding: FragmentLoginBinding) {
        binding.tvSignup.setText(getSignupText(getString(R.string.do_not_have_account), getString(R.string.signup)), TextView.BufferType.SPANNABLE)
        binding.bFBLogin.registerCallback(viewModel.getFBCallbackManager(), viewModel.getFBCallback())
        binding.bFBLogin.setReadPermissions("email")
        binding.bFBLogin.setFragment(this)
        viewModel.getUser().observe(this, Observer {
            if (it?.name != null) {
                // Sign in success, update UI with the signed-in user's information
                Toast.makeText(requireContext(), "signInWithCredential:success",
                        Toast.LENGTH_SHORT).show()

//                val direction = LoginFragmentDirections.ActionLoginFragmentToPlantListFragment()
//                findNavController().navigate(direction)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithCredential:failure")
                Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
            }
        })
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.getFBCallbackManager().onActivityResult(requestCode, resultCode, data)
    }

//    region

    fun getSignupText(text: String, keyword: String): SpannableStringBuilder {

        val spanText = SpannableStringBuilder()
        spanText.append(text)
        spanText.append(" ")
        spanText.append(keyword)
        spanText.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = textPaint.linkColor    // you can use custom color
                textPaint.isUnderlineText = false    // this remove the underline
            }
        }, spanText.length - keyword.length, spanText.length, 0)

        return spanText
//        textView.movementMethod = LinkMovementMethod.getInstance()
//        textView.setText(spanText, TextView.BufferType.SPANNABLE)

    }
    // endregion
}
