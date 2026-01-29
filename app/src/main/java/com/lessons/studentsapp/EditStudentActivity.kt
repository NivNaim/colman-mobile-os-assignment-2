package com.lessons.studentsapp

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.lessons.studentsapp.model.Model
import com.lessons.studentsapp.model.Student

class EditStudentActivity : AppCompatActivity() {

    private var originalId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        originalId = intent.getStringExtra("student_id")
        val student = Model.getStudentById(originalId ?: "")

        val nameEt: EditText = findViewById(R.id.edit_name_et)
        val idEt: EditText = findViewById(R.id.edit_id_et)
        val phoneEt: EditText = findViewById(R.id.edit_phone_et)
        val addressEt: EditText = findViewById(R.id.edit_address_et)
        val checkBox: CheckBox = findViewById(R.id.edit_cb)
        val deleteBtn: Button = findViewById(R.id.edit_delete_btn)
        val saveBtn: Button = findViewById(R.id.edit_save_btn)
        val cancelBtn: Button = findViewById(R.id.edit_cancel_btn)

        student?.let {
            nameEt.setText(it.name)
            idEt.setText(it.id)
            phoneEt.setText(it.phone)
            addressEt.setText(it.address)
            checkBox.isChecked = it.isChecked
        }

        cancelBtn.setOnClickListener { finish() }

        deleteBtn.setOnClickListener {
            if (student != null) {
                Model.removeStudent(student)
                finish()
            }
        }

        saveBtn.setOnClickListener {
            val updatedStudent = Student(
                name = nameEt.text.toString(),
                id = idEt.text.toString(),
                phone = phoneEt.text.toString(),
                address = addressEt.text.toString(),
                isChecked = checkBox.isChecked
            )

            Model.updateStudent(originalId ?: "", updatedStudent)
            finish()
        }
    }
}