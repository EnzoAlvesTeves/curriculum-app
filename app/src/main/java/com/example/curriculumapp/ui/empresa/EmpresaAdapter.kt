package com.example.curriculumapp.ui.empresa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.client.vaga.dto.EmpresaResponse
import com.example.curriculumapp.databinding.ItemEmpresaBinding

class EmpresaAdapter(
    private var empresas: List<EmpresaResponse>,
    private var vagaCounts: Map<Long, Int> = emptyMap(),
    private val onClick: (EmpresaResponse) -> Unit
) : RecyclerView.Adapter<EmpresaAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemEmpresaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEmpresaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empresa = empresas[position]
        holder.binding.tvNomeEmpresa.text = empresa.nome
        holder.binding.tvLocalizacao.text = "${empresa.cidade} - ${empresa.estado}"
        
        val count = vagaCounts[empresa.id] ?: 0
        holder.binding.tvEstadoBadge.text = "$count ${if (count == 1) "Vaga" else "Vagas"}"
        
        holder.itemView.setOnClickListener { onClick(empresa) }
    }

    override fun getItemCount(): Int = empresas.size

    fun updateData(newList: List<EmpresaResponse>, newCounts: Map<Long, Int>) {
        this.empresas = newList
        this.vagaCounts = newCounts
        notifyDataSetChanged()
    }
}
