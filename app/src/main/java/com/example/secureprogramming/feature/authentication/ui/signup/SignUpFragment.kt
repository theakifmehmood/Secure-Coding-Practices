package com.example.secureprogramming.feature.authentication.ui.signup

import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.secureprogramming.R
import com.example.secureprogramming.core.utils.MySharedPreferences
import com.example.secureprogramming.core.utils.ProgressDialogUtil
import com.example.secureprogramming.databinding.FragmentSignupBinding
import com.example.secureprogramming.feature.authentication.presentation.SignUpViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding


    private val viewModel: SignUpViewModel by viewModels()

    companion object {
        const val MAX_SIGNUP_ATTEMPTS = 5
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetTimer(true)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.name.observe(viewLifecycleOwner) {
            viewModel.validateName()
        }

        viewModel.email.observe(viewLifecycleOwner) {
            viewModel.validateEmail()
        }

        viewModel.password.observe(viewLifecycleOwner) {
            viewModel.validatePassword()
        }

        viewModel.signUpEnabled.observe(viewLifecycleOwner) {
            binding.signupButton.isEnabled = it
        }

        viewModel.user.observe(viewLifecycleOwner) {
            ProgressDialogUtil.hideProgressDialog()
            MySharedPreferences.resetSignupAttempts()
            viewModel.password.value?.let { MySharedPreferences.setHashKey(it) }
            findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
        }
        viewModel.exceptionMessage.observe(viewLifecycleOwner) {
            ProgressDialogUtil.hideProgressDialog()
            Toast.makeText(activity,it, Toast.LENGTH_SHORT).show()
            MySharedPreferences.incrementSignupAttempts()
            if (MySharedPreferences.getSignupAttempts() >= MAX_SIGNUP_ATTEMPTS) {
                resetTimer(false)
            }

        }

        binding.signupButton.setOnClickListener {
            if (MySharedPreferences.getSignupAttempts() < MAX_SIGNUP_ATTEMPTS) {
                ProgressDialogUtil.showProgressDialog(requireContext())
                viewModel.signUp()
                val lastSignupTime = System.currentTimeMillis()
                MySharedPreferences.setLastSignupTime(lastSignupTime)
            }
        }

        viewModel.signUpEnabled.observe(viewLifecycleOwner) {
            binding.signupButton.isEnabled = it
            if(it) {
                enableButtonColor()
            }else{
                disableButtonColor()
            }
        }


        binding.passwordLayout.setEndIconOnClickListener {
            val currentInputType = binding.signupPassword.inputType
            if (currentInputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.signupPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility_off)
            } else {
                binding.signupPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility)
            }
            binding.signupPassword.setSelection(binding.signupPassword.text?.length ?: 0)
        }


    }

    fun disableButtonColor(){
        binding.signupButton.setTextColor(resources.getColor(R.color.colorText2))
        binding.signupButton.setBackgroundResource(R.drawable.rounded_bg)
    }

    fun enableButtonColor(){
        binding.signupButton.setTextColor(resources.getColor(R.color.white))
        binding.signupButton.setBackgroundResource(R.drawable.rounded_bg_blue)
    }


    private fun resetTimer(isFragmentStarted: Boolean) {
        val lastSignUpTime = MySharedPreferences.getLastSignupTime()
        val currentTime = System.currentTimeMillis()
        val remainingTime = currentTime - lastSignUpTime
        if (remainingTime < 60000 && lastSignUpTime != 0L) {
            disableButtonColor()

            val countDownTimer = object : CountDownTimer((60000 - remainingTime), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val remainingSeconds = millisUntilFinished / 1000
                    viewModel.waitTime.value =
                        "You have reached the maximum number of signup attempts.\nPlease try again after ${remainingSeconds}s"
                }
                override fun onFinish() {
                    enableButtonColor()
                    MySharedPreferences.resetSignupAttempts()
                    viewModel.waitTime.value = ""
                }
            }
            countDownTimer.start()
        }else if(isFragmentStarted){
            enableButtonColor()
            MySharedPreferences.resetSignupAttempts()
            viewModel.waitTime.value = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}