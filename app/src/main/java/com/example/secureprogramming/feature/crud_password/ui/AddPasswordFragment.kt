package com.example.secureprogramming.feature.crud_password.ui

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
import com.example.secureprogramming.core.utils.ProgressDialogUtil
import com.example.secureprogramming.databinding.FragmentAddPasswordBinding
import com.example.secureprogramming.feature.crud_password.presentation.AddPasswordViewModel
import com.example.secureprogramming.feature.crud_password.presentation.model.PasswordPresentationModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPasswordFragment : Fragment() {

    private val viewModel: AddPasswordViewModel by viewModels()

    private lateinit var binding : FragmentAddPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPasswordBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passwordData = arguments?.getSerializable("password") as? PasswordPresentationModel
        if(passwordData != null){
            viewModel.updateValues(passwordData = passwordData)
            binding.addPasswordButton.text = "Update"
            binding.title.text = "UPDATE PASSWORD"
        }

        viewModel.response.observe(viewLifecycleOwner) {
            ProgressDialogUtil.hideProgressDialog()
            Toast.makeText(activity, it.second, Toast.LENGTH_SHORT).show()
            if (it.first) {
                findNavController().popBackStack()
            }
        }

        binding.addPasswordButton.setOnClickListener {
            ProgressDialogUtil.showProgressDialog(requireContext())
            viewModel.addPassword()
        }

        binding.backButton.setOnClickListener {

        }

        binding.passwordLayout.setEndIconOnClickListener {
            val currentInputType = binding.passwordEditText.inputType
            if (currentInputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                binding.passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility_off)
            } else {
                binding.passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordLayout.endIconDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_visibility)
            }
            binding.passwordEditText.setSelection(binding.passwordEditText.text?.length ?: 0)
        }

    }



}