package com.melashvili.fitnesstracker.services

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.melashvili.fitnesstracker.R
import com.melashvili.fitnesstracker.model.Workout

class WorkoutAdapter(private var workouts: MutableList<Workout>) :
    RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    class WorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.workoutName)
        val repsTextView: TextView = itemView.findViewById(R.id.workoutReps)
        val setsTextView: TextView = itemView.findViewById(R.id.workoutSets)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_workout, parent, false)
        return WorkoutViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workouts[position]
        holder.nameTextView.text = workout.name
        holder.repsTextView.text = "Reps: ${workout.reps}"
        holder.setsTextView.text = "Sets: ${workout.sets}"
    }

    override fun getItemCount(): Int = workouts.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateWorkouts(newWorkouts: List<Workout>) {
        workouts.clear()
        workouts.addAll(newWorkouts)
        notifyDataSetChanged()
    }
}
