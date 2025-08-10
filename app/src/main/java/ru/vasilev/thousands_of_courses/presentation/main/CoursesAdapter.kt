package ru.vasilev.thousands_of_courses.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vasilev.domain.model.Course
import ru.vasilev.thousands_of_courses.R
import ru.vasilev.thousands_of_courses.databinding.ItemCourseBinding

/**
 * Адаптер для списка курсов.
 * Добавлена логика для кнопки избранного.
 * @param onFavoriteClicked Лямбда-выражение, вызываемое при нажатии на кнопку избранного.
 */
class CoursesAdapter(
    private val onFavoriteClicked: (Course) -> Unit
) : ListAdapter<Course, CoursesAdapter.CourseViewHolder>(CourseDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = getItem(position)
        holder.bind(course, onFavoriteClicked)
    }

    inner class CourseViewHolder(private val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course, onFavoriteClicked: (Course) -> Unit) {

            binding.titleTextView.text = course.title

            binding.priceTextView.text = "Цена: ${course.price}"

            binding.rateTextView.text = "Рейтинг: ${course.rate.toString()}"

            binding.descriptionTextView.text = course.text

            val tintColor = if (course.hasLike) {
                ContextCompat.getColor(binding.root.context, R.color.green)
            } else {
                ContextCompat.getColor(binding.root.context, R.color.hint_white)
            }
            binding.bookmarkIcon.setColorFilter(tintColor)


            binding.bookmarkIcon.setOnClickListener {
                onFavoriteClicked(course)
            }
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
