package com.example.secureprogramming.feature.authentication.ui.update

import android.os.Bundle
import android.text.InputType
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
import com.example.secureprogramming.databinding.FragmentUpdatePasswordBinding
import com.example.secureprogramming.feature.authentication.presentation.UpdatePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentUpdatePasswordBinding

    private val viewModel: UpdatePasswordViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.password.observe(viewLifecycleOwner) {
            viewModel.validatePassword()
        }

        viewModel.newPassword.observe(viewLifecycleOwner) {
            viewModel.validateNewPassword()
        }

        viewModel.confirmNewPassword.observe(viewLifecycleOwner) {
            viewModel.validateNewConfirmPassword()
        }

        viewModel.signInEnabled.observe(viewLifecycleOwner) {
            binding.resetButton.isEnabled = it
            if(it) {
               enableButtonColor()
            }else{
              disableButtonColor()
            }
        }

        viewModel.responseMessage.observe(viewLifecycleOwner) {
            viewModel.password.value?.let { MySharedPreferences.setHashKey(it) }
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }

        viewModel.responseMessage.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it.first, Toast.LENGTH_SHORT).show()
            if (it.second) {
                findNavController().popBackStack()
            }
        }

        binding.resetButton.setOnClickListener {
            ProgressDialogUtil.showProgressDialog(requireContext())
            viewModel.resetPassword()
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

        binding.newPasswordLayout.setEndIconOnClickListener {
            val currentInputType = binding.newPasswordTextView.inputType
            if (currentInputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.newPasswordTextView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.newPasswordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility_off)
            } else {
                binding.newPasswordTextView.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.newPasswordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility)
            }
            binding.newPasswordTextView.setSelection(binding.newPasswordTextView.text?.length ?: 0)
        }


        binding.newConfirmPasswordLayout.setEndIconOnClickListener {
            val currentInputType = binding.newConfirmPasswordTextView.inputType
            if (currentInputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.newConfirmPasswordTextView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.newConfirmPasswordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility_off)
            } else {
                binding.newConfirmPasswordTextView.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.newConfirmPasswordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility)
            }
            binding.newConfirmPasswordTextView.setSelection(binding.newConfirmPasswordTextView.text?.length ?: 0)
        }

    }

    private fun disableButtonColor(){
        binding.resetButton.setTextColor(resources.getColor(R.color.colorText2))
        binding.resetButton.setBackgroundResource(R.drawable.rounded_bg)
    }

    private fun enableButtonColor(){
        binding.resetButton.setTextColor(resources.getColor(R.color.white))
        binding.resetButton.setBackgroundResource(R.drawable.rounded_bg_blue)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}