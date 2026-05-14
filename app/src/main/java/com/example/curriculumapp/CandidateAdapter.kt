package com.example.curriculumapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.client.candidato.dto.CandidatoDTO

class CandidateAdapter(private var candidates: List<CandidatoDTO>) :
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
        holder.tvName.text = candidate.nome
        // Pega o cargo da última experiência cadastrada, se houver
        holder.tvRole.text = candidate.experiencias.lastOrNull()?.cargo ?: "Candidato"
    }

    override fun getItemCount() = candidates.size

    fun updateCandidates(newCandidates: List<CandidatoDTO>) {
        this.candidates = newCandidates
        notifyDataSetChanged()
    }
}