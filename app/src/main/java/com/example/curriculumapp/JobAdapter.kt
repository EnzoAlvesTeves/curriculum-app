package com.example.curriculumapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.client.vaga.dto.VagaDTO
import java.text.NumberFormat
import java.util.*

class JobAdapter(private var jobs: List<VagaDTO>) :
    RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvJobTitle)
        val tvCompany: TextView = view.findViewById(R.id.tvCompany)
        val tvSalary: TextView = view.findViewById(R.id.tvSalary)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_job, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobs[position]
        holder.tvTitle.text = job.titulo
        holder.tvCompany.text = job.empresa
        
        val format = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        holder.tvSalary.text = "Salário: ${format.format(job.salario)}"
        
        holder.tvDescription.text = job.descricao
    }

    override fun getItemCount() = jobs.size

    fun updateJobs(newJobs: List<VagaDTO>) {
        this.jobs = newJobs
        notifyDataSetChanged()
    }
}