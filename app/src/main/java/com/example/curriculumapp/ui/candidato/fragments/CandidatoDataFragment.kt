package com.example.curriculumapp.ui.candidato.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.curriculumapp.client.candidato.CandidatoClient
import com.example.curriculumapp.client.candidato.dto.AlterarCandidatoRequest
import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import com.example.curriculumapp.client.candidato.dto.EnderecoDTO
import com.example.curriculumapp.databinding.FragmentCandidatoDataBinding
import com.example.curriculumapp.ui.candidato.CurriculoViewModel
import com.example.curriculumapp.util.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CandidatoDataFragment : Fragment() {

    private var _binding: FragmentCandidatoDataBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CurriculoViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCandidatoDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.candidato.observe(viewLifecycleOwner) { candidato ->
            candidato?.let { fillFields(it) }
        }

        binding.btnSave.setOnClickListener { save() }

        binding.etDataNascimento.setOnClickListener {
            DateUtils.showDatePicker(requireContext(), binding.etDataNascimento)
        }
        binding.etDataNascimento.isFocusable = false
        binding.etDataNascimento.isClickable = true
    }

    private fun fillFields(c: CandidatoDTO) {
        binding.etSexo.setText(c.sexo)
        binding.etDataNascimento.setText(DateUtils.formatToUi(c.dataNascimento))
        binding.etResumoProfissional.setText(c.resumoProfissional)
    }

    private fun save() {
        val current = viewModel.candidato.value ?: return
        
        val updatedSexo = binding.etSexo.text.toString()
        val updatedDataNascimento = DateUtils.formatToApi(binding.etDataNascimento.text.toString())
        val updatedResumo = binding.etResumoProfissional.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = if (current.id != null) {
                    val request = AlterarCandidatoRequest(
                        id = current.id,
                        nome = current.nome,
                        email = current.email,
                        sexo = updatedSexo,
                        telefone = current.telefone,
                        dataNascimento = updatedDataNascimento,
                        resumoProfissional = updatedResumo
                    )
                    CandidatoClient.api.alterar(request)
                } else {
                    val novoCandidato = current.copy(
                        sexo = updatedSexo,
                        dataNascimento = updatedDataNascimento,
                        resumoProfissional = updatedResumo
                    )
                    CandidatoClient.api.criar(novoCandidato)
                }
                
                withContext(Dispatchers.Main) {
                    viewModel.setCandidato(result)
                    Toast.makeText(context, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
