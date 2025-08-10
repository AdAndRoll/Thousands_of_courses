package ru.vasilev.thousands_of_courses.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vasilev.domain.model.Course
import ru.vasilev.thousands_of_courses.databinding.ItemCourseBinding

/**
 * Адаптер для списка курсов.
 * Обновлен для добавления префиксов к цене и рейтингу,
 * а также для отображения описания курса с ограничением в 2 строки.
 */
class CoursesAdapter :
    ListAdapter<Course, CoursesAdapter.CourseViewHolder>(CourseDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        holder.bind(course)
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {
            // Устанавливаем заголовок курса
            binding.titleTextView.text = course.title

            // Добавляем префикс "Цена:"
            binding.priceTextView.text = "Цена: ${course.price}"

            // Добавляем префикс "Рейтинг:"
            binding.rateTextView.text = "Рейтинг: ${course.rate.toString()}"

            // Устанавливаем описание курса
            // Предполагается, что в XML-файле item_course.xml для descriptionTextView
            // установлены атрибуты android:maxLines="2" и android:ellipsize="end"
            // для обрезки текста, который не умещается в две строки.
            binding.descriptionTextView.text = course.text
        }
    }

    object CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }
}
