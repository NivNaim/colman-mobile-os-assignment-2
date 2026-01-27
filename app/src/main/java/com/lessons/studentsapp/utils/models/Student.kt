package com.lessons.studentsapp.models

data class Student(
    val id: String,
    val fullName: String,
    val phoneNumber: String = "",
    val address: String = "",
    val isChecked: Boolean = false,
    val avatarResourceId: Int
) {

    fun isValid(): Boolean {
      return name.isNotBlank() && id.isNotBlank()
    }

    fun toggleChecked(): Student {
      return copy(isChecked = !isChecked)
    }
}
