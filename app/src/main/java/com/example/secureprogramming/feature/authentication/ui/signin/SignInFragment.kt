package com.example.secureprogramming.feature.authentication.ui.signin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.secureprogramming.R
import com.example.secureprogramming.core.utils.MySharedPreferences
import com.example.secureprogramming.core.utils.ProgressDialogUtil
import com.example.secureprogramming.core.utils.helper
import com.example.secureprogramming.databinding.FragmentSigninBinding
import com.example.secureprogramming.feature.authentication.presentation.SignInViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding

    private val viewModel: SignInViewModel by viewModels()

    companion object {
        const val MAX_LOGIN_ATTEMPTS = 5
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        FirebaseAuth.getInstance().currentUser
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MySharedPreferences.init(requireContext())
        resetTimer(true)

        viewModel.email.observe(viewLifecycleOwner) {
            viewModel.validateEmail()
        }

        viewModel.password.observe(viewLifecycleOwner) {
            viewModel.validatePassword()
        }

        viewModel.signInEnabled.observe(viewLifecycleOwner) {
            binding.loginButton.isEnabled = it
            if(it) {
            enableButtonColor()
            }else{
              disableButtonColor()
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {
            ProgressDialogUtil.hideProgressDialog()
            MySharedPreferences.resetLoginAttempts()
            viewModel.password.value?.let { MySharedPreferences.setHashKey(it) }
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }

        viewModel.exceptionMessage.observe(viewLifecycleOwner) {
            ProgressDialogUtil.hideProgressDialog()
            MySharedPreferences.incrementLoginAttempts()
            if (MySharedPreferences.getLoginAttempts() >= MAX_LOGIN_ATTEMPTS) {
                resetTimer(false)
            }
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
        }

        binding.loginButton.setOnClickListener {
            if (MySharedPreferences.getLoginAttempts() < MAX_LOGIN_ATTEMPTS) {
                ProgressDialogUtil.showProgressDialog(context = requireContext())
                viewModel.signIn()
                val lastLoginTime = System.currentTimeMillis()
                MySharedPreferences.setLastLoginTime(lastLoginTime)
            }
        }

        binding.signUpTextView.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)

        }

        binding.passwordLayout.setEndIconOnClickListener {
            val currentInputType = binding.passwordTextView.inputType
            if (currentInputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.passwordTextView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility_off)
            } else {
                binding.passwordTextView.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility)
            }
            binding.passwordTextView.setSelection(binding.passwordTextView.text?.length ?: 0)
        }

    }

    fun disableButtonColor(){
        binding.loginButton.setTextColor(resources.getColor(R.color.colorText2))
        binding.loginButton.setBackgroundResource(R.drawable.rounded_bg)
    }

    fun enableButtonColor(){
        binding.loginButton.setTextColor(resources.getColor(R.color.white))
        binding.loginButton.setBackgroundResource(R.drawable.rounded_bg_blue)
    }

    private fun resetTimer(isFragmentStarted: Boolean) {
            val lastLoginTime = MySharedPreferences.getLastLoginTime()
            val currentTime = System.currentTimeMillis()
            val remainingTime = currentTime - lastLoginTime
            if (remainingTime < 60000 && lastLoginTime != 0L) {
                disableButtonColor()

                val countDownTimer = object : CountDownTimer((60000 - remainingTime), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val remainingSeconds = millisUntilFinished / 1000
                        viewModel.waitTime.value =
                            "You have reached the maximum number of login attempts.\nPlease try again after ${remainingSeconds}s"
                    }
                    override fun onFinish() {
                        enableButtonColor()
                        MySharedPreferences.setLoginAttempts(0)
                        viewModel.waitTime.value = ""
                    }
                }
                countDownTimer.start()
            }else if(isFragmentStarted){
                enableButtonColor()
                MySharedPreferences.setLoginAttempts(0)
                viewModel.waitTime.value = ""
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}