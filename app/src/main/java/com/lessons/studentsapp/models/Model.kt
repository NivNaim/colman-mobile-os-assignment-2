package com.lessons.studentsapp.model

object Model {
    val students: MutableList<Student> = ArrayList()

    init {
        for (i in 0..10) {
            students.add(Student("Student $i", "$i", "050-00000$i", "Address $i", false))
        }
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun removeStudent(student: Student) {
        students.remove(student)
    }

    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }
    
    fun updateStudent(oldId: String, newStudent: Student) {
        val existing = getStudentById(oldId)
        existing?.apply {
            name = newStudent.name
            id = newStudent.id
            phone = newStudent.phone
            address = newStudent.address
            isChecked = newStudent.isChecked
        }
    }
}