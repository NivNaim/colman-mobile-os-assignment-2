package com.lessons.studentsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lessons.studentsapp.models.Student

class StudentAdapter(
    private var items: List<Student>,
    private val onStatusToggle: (id: String, index: Int) -> Unit,
    private val onItemPressed: ((id: String) -> Unit)? = null
) : RecyclerView.Adapter<StudentAdapter.StudentItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_row_layout, parent, false)
        return StudentItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentItemViewHolder, position: Int) {
        holder.render(items[position], onStatusToggle, onItemPressed)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newList: List<Student>) {
        items = newList
    }

    class StudentItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val avatarView: ImageView = view.findViewById(R.id.student_row_avatar)
        private val nameLabel: TextView = view.findViewById(R.id.student_row_name)
        private val idLabel: TextView = view.findViewById(R.id.student_row_id)
        private val statusCheck: CheckBox = view.findViewById(R.id.student_row_checkbox)

        fun render(
            student: Student,
            onStatusToggle: (id: String, index: Int) -> Unit,
            onItemPressed: ((id: String) -> Unit)?
        ) {
            nameLabel.text = student.name
            idLabel.text = itemView.context.getString(
                R.string.student_id_label,
                student.id
            )
            avatarView.setImageResource(student.avatarResourceId)

            // reset listener to avoid recycled state bugs
            statusCheck.setOnCheckedChangeListener(null)
            statusCheck.isChecked = student.isChecked

            statusCheck.setOnCheckedChangeListener { _, _ ->
                val index = bindingAdapterPosition
                if (index != RecyclerView.NO_POSITION) {
                    onStatusToggle(student.id, index)
                }
            }

            itemView.setOnClickListener {
                onItemPressed?.invoke(student.id)
            }
        }
    }
}
