package com.example.curriculumapp.ui.candidato

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.client.vaga.dto.VagaResponse
import com.example.curriculumapp.databinding.ItemCandidaturaBinding

class CandidaturaAdapter(
    private var candidaturas: List<VagaResponse>,
    private var empresaNames: Map<Long, String> = emptyMap(),
    private val onRemoveClick: (VagaResponse) -> Unit
) : RecyclerView.Adapter<CandidaturaAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCandidaturaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCandidaturaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vaga = candidaturas[position]
        holder.binding.tvTituloVaga.text = vaga.titulo
        holder.binding.tvNomeEmpresa.text = empresaNames[vaga.idEmpresa] ?: "Empresa não identificada"
        holder.binding.tvDataCandidatura.text = "Candidatado em: ${vaga.createdAt?.take(10) ?: "Data indisponível"}"
        
        holder.binding.btnRemove.setOnClickListener { onRemoveClick(vaga) }
    }

    override fun getItemCount(): Int = candidaturas.size

    fun updateData(newList: List<VagaResponse>, newEmpresaNames: Map<Long, String>) {
        this.candidaturas = newList
        this.empresaNames = newEmpresaNames
        notifyDataSetChanged()
    }
}
