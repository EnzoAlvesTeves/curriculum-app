package com.example.curriculumapp.ui.empresa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.client.vaga.dto.EmpresaResponse
import com.example.curriculumapp.databinding.ItemEmpresaBinding

class EmpresaAdapter(
    private var empresas: List<EmpresaResponse>,
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
        holder.binding.tvEstadoBadge.text = empresa.estado
        
        holder.itemView.setOnClickListener { onClick(empresa) }
    }

    override fun getItemCount(): Int = empresas.size

    fun updateList(newList: List<EmpresaResponse>) {
        this.empresas = newList
        notifyDataSetChanged()
    }
}
