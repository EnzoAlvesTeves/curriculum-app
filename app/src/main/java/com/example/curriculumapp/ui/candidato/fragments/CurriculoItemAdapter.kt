package com.example.curriculumapp.ui.candidato.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.curriculumapp.databinding.ItemCurriculoGenericBinding

data class CurriculoItem(
    val id: Long?,
    val title: String,
    val subtitle: String,
    val date: String? = null
)

class CurriculoItemAdapter(
    private var items: List<CurriculoItem>,
    private val onDelete: (CurriculoItem) -> Unit
) : RecyclerView.Adapter<CurriculoItemAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCurriculoGenericBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCurriculoGenericBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvSubtitle.text = item.subtitle
        holder.binding.tvDate.text = item.date
        holder.binding.btnDelete.setOnClickListener { onDelete(item) }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<CurriculoItem>) {
        this.items = newList
        notifyDataSetChanged()
    }
}
