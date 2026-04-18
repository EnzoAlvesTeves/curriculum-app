package com.example.curriculumapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CandidateAdapter(private var candidates: List<Candidate>) :
    RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    class CandidateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvCandidateName)
        val tvRole: TextView = view.findViewById(R.id.tvCandidateRole)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_candidate, parent, false)
        return CandidateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.tvName.text = candidate.name
        holder.tvRole.text = candidate.role
    }

    override fun getItemCount() = candidates.size

    fun updateCandidates(newCandidates: List<Candidate>) {
        this.candidates = newCandidates
        notifyDataSetChanged()
    }
}