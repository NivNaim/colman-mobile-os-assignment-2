package com.lessons.studentsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lessons.studentsapp.model.Student

interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onStudentClick(student: Student?)
}

class StudentAdapter(
    private val students: List<Student>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView = itemView.findViewById(R.id.student_row_name_tv)
        val idTv: TextView = itemView.findViewById(R.id.student_row_id_tv)
        val checkBox: CheckBox = itemView.findViewById(R.id.student_row_cb)
        val image: ImageView = itemView.findViewById(R.id.student_row_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        // We used "student_list_row" in the XML step. Make sure this matches that file name!
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_row_layout, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.nameTv.text = student.name
        holder.idTv.text = student.id
        holder.image.setImageResource(R.drawable.ic_launcher_foreground)

        // Clear listener to avoid bugs when scrolling
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.isChecked = student.isChecked

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            student.isChecked = isChecked
        }

        holder.itemView.setOnClickListener {
            listener.onStudentClick(student)
        }
    }

    override fun getItemCount(): Int = students.size
}