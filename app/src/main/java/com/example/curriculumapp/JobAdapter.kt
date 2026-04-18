package com.example.curriculumapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JobAdapter(private var jobs: List<Job>) :
    RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvJobTitle)
        val tvCandidates: TextView = view.findViewById(R.id.tvCandidatesCount)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        holder.tvTitle.text = job.title
        holder.tvCandidates.text = "${job.candidatesCount} Candidatos"
        holder.tvStatus.text = job.status
        
        // Simple logic to change status color if needed, but keeping it orange as per image
    }

    override fun getItemCount() = jobs.size

    fun updateJobs(newJobs: List<Job>) {
        this.jobs = newJobs
        notifyDataSetChanged()
    }
}