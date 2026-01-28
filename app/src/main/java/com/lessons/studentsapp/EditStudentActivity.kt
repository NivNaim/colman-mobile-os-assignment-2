package com.lessons.studentsapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lessons.studentsapp.models.Model
import com.lessons.studentsapp.models.Student
import com.lessons.studentsapp.constants.Constants

class EditStudentActivity : AppCompatActivity() {

    private lateinit var avatarView: ImageView
    private lateinit var nameInput: EditText
    private lateinit var idInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var activeCheck: CheckBox

    private lateinit var btnDelete: Button
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private var loadedStudent: Student? = null
    private var initialStudentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        initialStudentId = intent.getStringExtra(Constants.EXTRA_STUDENT_ID)
        if (initialStudentId == null) {
            showToast("Missing student ID")
            finish()
            return
        }

        initViews()
        initListeners()
        fetchStudent()
    }

    private fun initViews() {
        avatarView = findViewById(R.id.edit_student_avatar)
        nameInput = findViewById(R.id.edit_student_name)
        idInput = findViewById(R.id.edit_student_id)
        phoneInput = findViewById(R.id.edit_student_phone)
        addressInput = findViewById(R.id.edit_student_address)
        activeCheck = findViewById(R.id.edit_student_checked)

        btnDelete = findViewById(R.id.edit_student_delete)
        btnSave = findViewById(R.id.edit_student_save)
        btnCancel = findViewById(R.id.edit_student_cancel)
    }

    private fun initListeners() {
        btnCancel.setOnClickListener { finish() }
        btnSave.setOnClickListener { applyChanges() }
        btnDelete.setOnClickListener { showDeleteDialog() }
    }

    private fun fetchStudent() {
        val student = Model.shared.getStudentById(initialStudentId!!)
        if (student == null) {
            showToast("Student not found")
            finish()
            return
        }

        loadedStudent = student
        fillFields(student)
    }

    private fun fillFields(student: Student) {
        avatarView.setImageResource(student.avatarResourceId)
        nameInput.setText(studentstudentsapp)
        idInput.setText(student.id)
        phoneInput.setText(studentphoneNumber)
        addressInput.setText(student.address)
        activeCheck.isChecked = student.isChecked
    }

    private fun applyChanges() {
        val updatedName = nameInput.text.toString().trim()
        val updatedId = idInput.text.toString().trim()

        if (updatedName.isEmpty() || updatedId.isEmpty()) {
            showToast("Name and ID are required")
            return
        }

        if (updatedId != initialStudentId &&
            !Model.shared.isIdUnique(updatedId, initialStudentId)
        ) {
            showToast("Student with ID $updatedId already exists")
            return
        }

        val updatedStudent = Student(
            id = updatedId,
            fullName = updatedName,
            phoneNumber = phoneInput.text.toString().trim(),
            address = addressInput.text.toString().trim(),
            isChecked = activeCheck.isChecked,
            avatarResourceId = loadedStudent?.avatarResourceId
                ?: Constants.DEFAULT_AVATAR_RES_ID
        )

        try {
            Model.shared.updateStudent(initialStudentId!!, updatedStudent)
            showToast("Student updated successfully")
            finish()
        } catch (ex: Exception) {
            showToast(ex.message ?: "Update failed")
        }
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Delete ${loadedStudent?studentsapp}?")
            .setPositiveButton("Delete") { _, _ -> removeStudent() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun removeStudent() {
        val success = Model.shared.deleteStudent(initialStudentId!!)
        if (success) {
            showToast("Student deleted")
            finish()
        } else {
            showToast("Could not delete student")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}