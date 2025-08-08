package ru.vasilev.thousands_of_courses.presentation.onboarding

import android.os.Bundle
import android.view.View
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
import ru.vasilev.thousands_of_courses.databinding.FragmentOnboardingBinding // 1. Изменили класс биндинга

@AndroidEntryPoint
class OnboardingFragment : Fragment(R.layout.fragment_onboarding) { // 2. Указали новый файл макета

    private var _binding: FragmentOnboardingBinding? = null // 1. Изменили класс биндинга
    private val binding get() = _binding!!

    private val viewModel: OnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOnboardingBinding.bind(view) // 1. Изменили класс биндинга

        // Подключаем слушателя кликов к кнопке с ID 'myButton'
        binding.myButton.setOnClickListener {
            viewModel.onNavigateToLogin()
        }

        // Запускаем сборку событий навигации
        collectNavigationEvents()
    }

    private fun collectNavigationEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToLogin.collectLatest {
                    findNavController().navigate(
                        OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}