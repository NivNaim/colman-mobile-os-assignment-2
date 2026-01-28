package com.lessons.studentsapp.models

class Model private constructor() {

  private val studentList = mutableListOf<Student>()

  companion object {
    val shared: Model by lazy { Model() }
  }

  fun initializeSampleData(defaultAvatarId: Int) {
    if (studentList.isNotEmpty()) return

    val samples = listOf(
      Student("901234", "Liam Carter", "0529876543", "12 Maple Street", false, defaultAvatarId),
      Student("812345", "Emma Rodriguez", "0531237894", "45 Ocean Avenue", false, defaultAvatarId),
      Student("723456", "Noah Kim", "0544561239", "8 Pine Road", false, defaultAvatarId),
      Student("634567", "Sophia Nguyen", "0553219876", "27 Sunset Blvd", false, defaultAvatarId),
      Student("545678", "Daniel Cohen", "0566543210", "3 Valley Drive", false, defaultAvatarId)
    )

    studentList.addAll(samples)
  }

  fun getAllStudents(): List<Student> = studentList.toList()

  fun findStudent(id: String): Student? = studentList.firstOrNull { it.id == id }

  fun addStudent(student: Student) {
    validateStudent(student)

    if (studentList.any { it.id == student.id }) {
      throw IllegalArgumentException("Student with ID ${student.id} already exists")
    }

    studentList.add(student)
  }

  fun updateStudent(originalId: String, newStudent: Student) {
    validateStudent(newStudent)

    val index = studentList.indexOfFirst { it.id == originalId }
    if (index < 0) {
      throw IllegalArgumentException("Student with ID $originalId not found")
    }

    if (originalId != newStudent.id && studentList.any { it.id == newStudent.id }) {
      throw IllegalArgumentException("Student with ID ${newStudent.id} already exists")
    }

    studentList[index] = newStudent
  }

  fun removeStudent(id: String): Boolean = studentList.removeAll { it.id == id }

  fun toggleChecked(id: String): Boolean {
    val index = studentList.indexOfFirst { it.id == id }
    if (index < 0) return false

    studentList[index] = studentList[index].toggleChecked()
    return true
  }

  fun isIdUnique(id: String, ignoreId: String? = null): Boolean {
    return studentList.none { it.id == id && it.id != ignoreId }
  }

  private fun validateStudent(student: Student) {
    if (!student.isValid()) {
      throw IllegalArgumentException("Student data is invalid")
    }
  }
}
