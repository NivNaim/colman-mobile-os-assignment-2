package com.lessons.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lessons.studentsapp.model.Model

class StudentDetailsActivity : AppCompatActivity() {

    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        studentId = intent.getStringExtra("student_id")
    }

    override fun onResume() {
        super.onResume()
        val student = Model.getStudentById(studentId ?: "")

        if (student == null) {
            finish()
            return
        }

        findViewById<TextView>(R.id.student_details_name).text = "Name: ${student.name}"
        findViewById<TextView>(R.id.student_details_id).text = "ID: ${student.id}"
        findViewById<TextView>(R.id.student_details_phone).text = "Phone: ${student.phone}"
        findViewById<TextView>(R.id.student_details_address).text = "Address: ${student.address}"
        findViewById<CheckBox>(R.id.student_details_checked).apply {
            isChecked = student.isChecked
            isEnabled = false // Read-only in this view
        }

        findViewById<Button>(R.id.student_details_edit_btn).setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student_id", student.id)
            startActivity(intent)
        }
    }
}