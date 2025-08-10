package ru.vasilev.thousands_of_courses.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.vasilev.thousands_of_courses.R
import ru.vasilev.thousands_of_courses.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: CoursesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = CoursesAdapter()
        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.coursesRecyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Отслеживание списка курсов и обновление адаптера
            viewModel.courses.collectLatest { courses ->
                adapter.submitList(courses)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            // Отслеживание состояния загрузки и управление видимостью ProgressBar
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                // Также можно скрыть RecyclerView, чтобы он не отображался до загрузки данных
                binding.coursesRecyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
        }
    }

    /**
     * Установка слушателя для кнопки сортировки.
     * При нажатии вызывается метод toggleSort() в ViewModel.
     */
    private fun setupListeners() {
        binding.filterButton.setOnClickListener {
            viewModel.toggleSort()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
