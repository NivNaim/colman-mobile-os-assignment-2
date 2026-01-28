package com.lessons.studentsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lessons.studentsapp.models.Model
import com.lessons.studentsapp.utils.Constants.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var listView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter

    private var previousCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        applyInsets()
        prepareInitialData()
        initList()
        initAddButton()

        previousCount = Model.shared.getAllStudents().size
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }

    private fun applyInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }
    }

    private fun prepareInitialData() {
        Model.shared.initializeSampleData(
            defaultAvatarId = Constants.DEFAULT_AVATAR_RES_ID
        )
    }

    private fun initList() {
        listView = findViewById(R.id.students_recycler_view)
        listView.layoutManager = LinearLayoutManager(this)

        studentAdapter = StudentAdapter(
            items = Model.shared.getAllStudents(),
            onStatusToggle = { studentId, index ->
                Model.shared.toggleChecked(studentId)
                studentAdapter.updateData(Model.shared.getAllStudents())
                studentAdapter.notifyItemChanged(index)
            },
            onItemPressed = { studentId ->
                openStudentDetails(studentId)
            }
        )

        listView.adapter = studentAdapter
    }

    private fun initAddButton() {
        findViewById<FloatingActionButton>(R.id.add_student_fab)
            .setOnClickListener {
                startActivity(
                    Intent(this, AddStudentActivity::class.java)
                )
            }
    }

    private fun refreshList() {
        val updatedList = Model.shared.getAllStudents()
        val currentCount = updatedList.size

        studentAdapter.updateData(updatedList)

        when {
            currentCount > previousCount ->
                studentAdapter.notifyItemInserted(currentCount - 1)

            currentCount < previousCount ->
                studentAdapter.notifyDataSetChanged()

            else ->
                studentAdapter.notifyDataSetChanged()
        }

        previousCount = currentCount
    }

    private fun openStudentDetails(studentId: String) {
        val detailsIntent = Intent(this, StudentDetailsActivity::class.java)
        detailsIntent.putExtra(Constants.EXTRA_STUDENT_ID, studentId)
        startActivity(detailsIntent)
    }
}