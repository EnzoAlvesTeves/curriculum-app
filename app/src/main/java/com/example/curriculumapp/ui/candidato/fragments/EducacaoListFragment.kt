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
import com.example.curriculumapp.client.candidato.EducacaoClient
import com.example.curriculumapp.client.candidato.dto.EducacaoDTO
import com.example.curriculumapp.databinding.DialogEducacaoBinding
import com.example.curriculumapp.databinding.FragmentCurriculoListBinding
import com.example.curriculumapp.ui.candidato.CurriculoViewModel
import com.example.curriculumapp.util.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EducacaoListFragment : Fragment() {

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
            candidato?.educacoes?.let { list ->
                adapter.updateList(list.map { 
                    CurriculoItem(
                        it.id, 
                        it.curso ?: "", 
                        it.instituicao ?: "", 
                        "${DateUtils.formatToUi(it.dataInicio)} - ${DateUtils.formatToUi(it.dataFim)}"
                    )
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
        val dialogBinding = DialogEducacaoBinding.inflate(layoutInflater)
        
        dialogBinding.etDataInicio.setOnClickListener { DateUtils.showDatePicker(requireContext(), dialogBinding.etDataInicio) }
        dialogBinding.etDataFim.setOnClickListener { DateUtils.showDatePicker(requireContext(), dialogBinding.etDataFim) }
        dialogBinding.etDataInicio.isFocusable = false
        dialogBinding.etDataFim.isFocusable = false

        AlertDialog.Builder(requireContext())
            .setTitle("Adicionar Educação")
            .setView(dialogBinding.root)
            .setPositiveButton("Salvar") { _, _ ->
                val novo = EducacaoDTO(
                    instituicao = dialogBinding.etInstituicao.text.toString(),
                    curso = dialogBinding.etCurso.text.toString(),
                    grau = dialogBinding.etGrau.text.toString(),
                    dataInicio = DateUtils.formatToApi(dialogBinding.etDataInicio.text.toString()),
                    dataFim = DateUtils.formatToApi(dialogBinding.etDataFim.text.toString())
                )
                saveItem(novo)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun saveItem(item: EducacaoDTO) {
        val candidatoId = viewModel.candidato.value?.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                EducacaoClient.api.criar(candidatoId, item)
                withContext(Dispatchers.Main) {
                    viewModel.loadCandidato() // Refresh everything
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro ao salvar educação", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteItem(item: CurriculoItem) {
        val candidatoId = viewModel.candidato.value?.id ?: return
        val educacaoId = item.id ?: return
        CoroutineScope(Dispatchers.IO).launch {
            try {
                EducacaoClient.api.deletar(candidatoId, educacaoId)
                withContext(Dispatchers.Main) {
                    viewModel.loadCandidato()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro ao deletar educação", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
