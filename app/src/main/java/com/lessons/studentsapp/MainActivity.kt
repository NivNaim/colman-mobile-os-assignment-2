package com.lessons.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class MainActivity : AppCompatActivity() {

    private var adapter: StudentAdapter? = null
    private var studentList: MutableList<Student>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentList = Model.students
        val recyclerView: RecyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = StudentAdapter(studentList!!, object : OnItemClickListener {
            override fun onItemClick(position: Int) {}
            override fun onStudentClick(student: Student?) {
                val intent = Intent(this@MainActivity, StudentDetailsActivity::class.java)
                intent.putExtra("student_id", student?.id)
                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter

        val addBtn: FloatingActionButton = findViewById(R.id.add_student_fab)
        addBtn.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }
}