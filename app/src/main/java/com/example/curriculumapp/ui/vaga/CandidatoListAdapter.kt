package com.example.curriculumapp.ui.vaga

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.client.usuario.dto.UsuarioResponse
import com.example.curriculumapp.databinding.ItemCandidatoListBinding

class CandidatoListAdapter(
    private var candidatos: List<UsuarioResponse>
) : RecyclerView.Adapter<CandidatoListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCandidatoListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCandidatoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val candidato = candidatos[position]
        holder.binding.tvNome.text = "${candidato.nome} ${candidato.sobrenome}"
        holder.binding.tvEmail.text = candidato.email
        holder.binding.tvTelefone.text = candidato.telefone ?: "Telefone não informado"
    }

    override fun getItemCount(): Int = candidatos.size

    fun updateList(newList: List<UsuarioResponse>) {
        this.candidatos = newList
        notifyDataSetChanged()
    }
}
