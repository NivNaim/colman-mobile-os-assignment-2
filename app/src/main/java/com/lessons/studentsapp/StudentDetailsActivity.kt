package com.lessons.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lessons.studentsapp.models.Model
import com.lessons.studentsapp.models.Student
import com.lessons.studentsapp.utils.Constants.Constants

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var avatarView: ImageView
    private lateinit var nameLabel: TextView
    private lateinit var idLabel: TextView
    private lateinit var phoneLabel: TextView
    private lateinit var addressLabel: TextView
    private lateinit var statusLabel: TextView

    private lateinit var editButton: Button
    private lateinit var backButton: Button

    private var selectedStudentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        selectedStudentId = intent.getStringExtra(Constants.EXTRA_STUDENT_ID)
        if (selectedStudentId == null) {
            showToast("Student ID not provided")
            finish()
            return
        }

        setupViews()
        setupActions()
    }

    override fun onResume() {
        super.onResume()
        refreshStudent()
    }

    private fun setupViews() {
        avatarView = findViewById(R.id.student_details_avatar)
        nameLabel = findViewById(R.id.student_details_name)
        idLabel = findViewById(R.id.student_details_id)
        phoneLabel = findViewById(R.id.student_details_phone)
        addressLabel = findViewById(R.id.student_details_address)
        statusLabel = findViewById(R.id.student_details_checked)

        editButton = findViewById(R.id.student_details_edit)
        backButton = findViewById(R.id.student_details_back)
    }

    private fun setupActions() {
        editButton.setOnClickListener {
            val editIntent = Intent(this, EditStudentActivity::class.java)
            editIntent.putExtra(Constants.EXTRA_STUDENT_ID, selectedStudentId)
            startActivity(editIntent)
        }

        backButton.setOnClickListener { finish() }
    }

    private fun refreshStudent() {
        val student = Model.shared.getStudentById(selectedStudentId!!)
        if (student == null) {
            showToast("Student not found")
            finish()
            return
        }

        bindStudent(student)
    }

    private fun bindStudent(student: Student) {
        avatarView.setImageResource(student.avatarResourceId)
        nameLabel.text = studentstudentsapp
        idLabel.text = student.id
        phoneLabel.text = studentphoneNumber.ifEmpty { "N/A" }
        addressLabel.text = student.address.ifEmpty { "N/A" }
        statusLabel.text = if (student.isChecked) "✓" else "✗"
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}