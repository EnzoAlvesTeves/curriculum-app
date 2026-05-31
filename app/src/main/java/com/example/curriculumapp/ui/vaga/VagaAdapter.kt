package com.example.curriculumapp.ui.vaga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.client.vaga.dto.VagaResponse
import com.example.curriculumapp.databinding.ItemVagaBinding

class VagaAdapter(
    private var vagas: List<VagaResponse>,
    private var empresaNames: Map<Long, String> = emptyMap(),
    private var candidatoCounts: Map<Long, Int> = emptyMap(),
    private val onClick: (VagaResponse) -> Unit
) : RecyclerView.Adapter<VagaAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVagaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVagaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vaga = vagas[position]
        holder.binding.tvTituloVaga.text = vaga.titulo
        
        holder.binding.tvNomeEmpresa.text = empresaNames[vaga.idEmpresa] ?: "Empresa não identificada"
        
        holder.binding.tvDataCriacao.text = "Criada em: ${vaga.createdAt?.take(10) ?: "Data indisponível"}"
        
        val count = candidatoCounts[vaga.id] ?: 0
        holder.binding.tvCandidatosBadge.text = "$count ${if (count == 1) "Candidato" else "Candidatos"}"
        
        holder.itemView.setOnClickListener { onClick(vaga) }
    }

    override fun getItemCount(): Int = vagas.size

    fun updateData(
        newVagas: List<VagaResponse>, 
        newEmpresaNames: Map<Long, String>,
        newCandidatoCounts: Map<Long, Int> = emptyMap()
    ) {
        this.vagas = newVagas
        this.empresaNames = newEmpresaNames
        this.candidatoCounts = newCandidatoCounts
        notifyDataSetChanged()
    }
}
