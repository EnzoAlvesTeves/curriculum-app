package com.example.curriculumapp.ui.candidato

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.client.vaga.dto.VagaResponse
import com.example.curriculumapp.databinding.ItemVagaPublicaBinding

class VagaPublicaAdapter(
    private var vagas: List<VagaResponse>,
    private var empresaNames: Map<Long, String> = emptyMap(),
    private val onDetailsClick: (VagaResponse) -> Unit,
    private val onApplyClick: (VagaResponse) -> Unit
) : RecyclerView.Adapter<VagaPublicaAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVagaPublicaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVagaPublicaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vaga = vagas[position]
        holder.binding.tvTituloVaga.text = vaga.titulo
        holder.binding.tvNomeEmpresa.text = empresaNames[vaga.idEmpresa] ?: "Empresa não identificada"
        holder.binding.tvDescricao.text = vaga.descricao
        
        holder.binding.btnDetails.setOnClickListener { onDetailsClick(vaga) }
        holder.binding.btnApply.setOnClickListener { onApplyClick(vaga) }
    }

    override fun getItemCount(): Int = vagas.size

    fun updateData(newVagas: List<VagaResponse>, newEmpresaNames: Map<Long, String>) {
        this.vagas = newVagas
        this.empresaNames = newEmpresaNames
        notifyDataSetChanged()
    }
}
