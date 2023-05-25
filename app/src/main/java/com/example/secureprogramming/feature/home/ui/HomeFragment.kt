package com.example.secureprogramming.feature.home.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.secureprogramming.R
import com.example.secureprogramming.databinding.FragmentHomeBinding
import com.example.secureprogramming.feature.crud_password.presentation.model.PasswordPresentationModel
import com.example.secureprogramming.feature.home.presentation.HomeViewModel
import com.example.secureprogramming.feature.home.ui.widget.HomeAdaptor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: HomeAdaptor

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.retrievePassword()
        viewModel.password.observe(viewLifecycleOwner){
            adapter = HomeAdaptor(object : HomeAdaptor.OnItemClickListener {
                override fun onItemClick(password: PasswordPresentationModel) {
                    val bundle = Bundle().apply {
                        putSerializable("password", password)
                    }
                    findNavController().navigate(R.id.action_homeFragment_to_addPasswordFragment,bundle)
                }
            })
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(context)

            adapter.updatePasswordList(it)
        }

        binding.floatingButton.setOnClickListener {
          findNavController().navigate(R.id.action_homeFragment_to_addPasswordFragment)
        }

        binding.logoutImageView.setOnClickListener { view ->
                val popup = PopupMenu(requireContext(), view)
                popup.menuInflater.inflate(R.menu.home_menu, popup.menu)

                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_reset -> {
                            findNavController().navigate(R.id.action_homeFragment_to_updatePasswordFragment)
                            true
                        }
                        R.id.action_logout -> {
                            viewModel.logout()
                            val navController = findNavController()
                            navController.navigate(R.id.action_homeFragment_to_signInFragment)
                            true
                        }
                        else -> false
                    }
                }

                popup.show()
            }



    }

}