package ru.vasilev.thousands_of_courses.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vasilev.thousands_of_courses.R
import ru.vasilev.thousands_of_courses.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        setupListeners()
        observeViewModel()


        binding.textViewRegister.isEnabled = false
        binding.textViewForgotPassword.isEnabled = false
    }

    private fun setupListeners() {
        binding.textInputLayoutEmail.editText?.doAfterTextChanged { text ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.textInputLayoutPassword.editText?.doAfterTextChanged { text ->
            viewModel.onPasswordChanged(text.toString())
        }

        binding.myButton.setOnClickListener {

            if (binding.myButton.isEnabled) {

                viewModel.onLoginClicked()
            } else {

                Toast.makeText(
                    context,
                    "Пожалуйста, заполните все поля корректно",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("LoginFragment", "Пользователь пытался нажать неактивную кнопку. Ввод некорректен.")
            }
        }

        binding.buttonVk.setOnClickListener {
            viewModel.onVkClicked()
        }

        binding.buttonOk.setOnClickListener {
            viewModel.onOkClicked()
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.isLoginButtonEnabled.collectLatest { isEnabled ->
                        binding.myButton.isEnabled = isEnabled
                        Log.d("LoginFragment", "Состояние кнопки входа: $isEnabled")
                    }
                }

                launch {
                    viewModel.navigateToMainScreen.collectLatest {

                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                        Log.d("LoginFragment", "Получено событие для перехода на Главный экран.")
                    }
                }


                launch {
                    viewModel.navigateToVk.collectLatest {
                        openUrlInBrowser("https://vk.com/")
                    }
                }


                launch {
                    viewModel.navigateToOk.collectLatest {
                        openUrlInBrowser("https://ok.ru/")
                    }
                }
            }
        }
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}