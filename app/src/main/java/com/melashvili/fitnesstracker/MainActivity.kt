package com.melashvili.fitnesstracker

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.melashvili.fitnesstracker.model.Workout
import com.melashvili.fitnesstracker.services.WorkoutAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var workoutAdapter: WorkoutAdapter

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        database = FirebaseDatabase.getInstance("https://fitness-tracker-62872-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("workouts")

        workoutAdapter = WorkoutAdapter(mutableListOf())
        recyclerView.adapter = workoutAdapter

        fetchWorkouts()
    }

    private fun fetchWorkouts() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val workouts = mutableListOf<Workout>()
                Log.d("FirebaseDB", "DataSnapshot: ${snapshot.value}")
                for (data in snapshot.children) {
                    Log.d("FirebaseDB", "ChildSnapshot: ${data.key} -> ${data.value}")
                    val workout = data.getValue(Workout::class.java)
                    workout?.let { workouts.add(it) }
                }
                workoutAdapter.updateWorkouts(workouts)
                Log.d("FirebaseDB", "Workouts size: ${workouts.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Failed to load workouts: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
