package com.example.curriculumapp.ui.candidato.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.curriculumapp.client.candidato.HabilidadeClient
import com.example.curriculumapp.client.candidato.dto.HabilidadeDTO
import com.example.curriculumapp.databinding.DialogHabilidadeBinding
import com.example.curriculumapp.databinding.FragmentCurriculoListBinding
import com.example.curriculumapp.ui.candidato.CurriculoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HabilidadeListFragment : Fragment() {

    private var _binding: FragmentCurriculoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CurriculoViewModel by activityViewModels()
    private lateinit var adapter: CurriculoItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCurriculoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        
        viewModel.candidato.observe(viewLifecycleOwner) { candidato ->
            candidato?.habilidades?.let { list ->
                adapter.updateList(list.map { 
                    CurriculoItem(it.id, it.descricao ?: "", it.nivel ?: "")
                })
            }
        }

        binding.btnAdd.setOnClickListener { showAddDialog() }
    }

    private fun setupRecyclerView() {
        adapter = CurriculoItemAdapter(emptyList()) { item ->
            deleteItem(item)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
    }

    private fun showAddDialog() {
        val dialogBinding = DialogHabilidadeBinding.inflate(layoutInflater)
        AlertDialog.Builder(requireContext())
            .setTitle("Adicionar Habilidade")
            .setView(dialogBinding.root)
            .setPositiveButton("Salvar") { _, _ ->
                val novo = HabilidadeDTO(
                    descricao = dialogBinding.etDescricao.text.toString(),
                    nivel = dialogBinding.etNivel.text.toString()
                )
                saveItem(novo)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun saveItem(item: HabilidadeDTO) {
        val candidatoId = viewModel.candidato.value?.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                HabilidadeClient.api.criar(candidatoId, item)
                withContext(Dispatchers.Main) {
                    viewModel.loadCandidato()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro ao salvar habilidade", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteItem(item: CurriculoItem) {
        val candidatoId = viewModel.candidato.value?.id ?: return
        val id = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                HabilidadeClient.api.deletar(candidatoId, id)
                withContext(Dispatchers.Main) {
                    viewModel.loadCandidato()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro ao deletar habilidade", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
