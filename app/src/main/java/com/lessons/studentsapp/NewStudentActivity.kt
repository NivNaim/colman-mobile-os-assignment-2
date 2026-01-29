package com.lessons.studentsapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class NewStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        val nameEt: EditText = findViewById(R.id.new_student_name)
        val idEt: EditText = findViewById(R.id.new_student_id)
        val phoneEt: EditText = findViewById(R.id.new_student_phone)
        val addressEt: EditText = findViewById(R.id.new_student_address)
        val saveBtn: Button = findViewById(R.id.new_student_save_btn)
        val cancelBtn: Button = findViewById(R.id.new_student_cancel_btn)

        saveBtn.setOnClickListener {
            val student = Student(
                name = nameEt.text.toString(),
                id = idEt.text.toString(),
                phone = phoneEt.text.toString(),
                address = addressEt.text.toString(),
                isChecked = false
            )
            Model.addStudent(student)
            finish() // Close activity and go back
        }

        cancelBtn.setOnClickListener {
            finish()
        }
    }
}