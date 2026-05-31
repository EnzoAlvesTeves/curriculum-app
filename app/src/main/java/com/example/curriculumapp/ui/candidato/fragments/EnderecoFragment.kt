package com.example.curriculumapp.ui.candidato.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.curriculumapp.client.candidato.EnderecoClient
import com.example.curriculumapp.client.candidato.dto.EnderecoDTO
import com.example.curriculumapp.databinding.FragmentEnderecoBinding
import com.example.curriculumapp.ui.candidato.CurriculoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EnderecoFragment : Fragment() {

    private var _binding: FragmentEnderecoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CurriculoViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEnderecoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.candidato.observe(viewLifecycleOwner) { candidato ->
            candidato?.endereco?.let { fillFields(it) }
        }

        binding.btnSave.setOnClickListener { save() }
    }

    private fun fillFields(e: EnderecoDTO) {
        binding.etCep.setText(e.cep)
        binding.etRua.setText(e.rua)
        binding.etComplemento.setText(e.complemento)
        binding.etNumero.setText(e.numero)
        binding.etBairro.setText(e.bairro)
        binding.etCidade.setText(e.cidade)
        binding.etEstado.setText(e.estado)
    }

    private fun save() {
        val currentCandidato = viewModel.candidato.value ?: return
        val candidatoId = currentCandidato.id ?: return

        val updatedEndereco = EnderecoDTO(
            cep = binding.etCep.text.toString(),
            rua = binding.etRua.text.toString(),
            complemento = binding.etComplemento.text.toString(),
            numero = binding.etNumero.text.toString(),
            bairro = binding.etBairro.text.toString(),
            cidade = binding.etCidade.text.toString(),
            estado = binding.etEstado.text.toString()
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Here we call EnderecoApi.alterar or criar. 
                // Since the API uses candidatoId as path, let's use alterar/criar logic
                val result = if (currentCandidato.endereco?.id != null) {
                    EnderecoClient.api.alterar(candidatoId, updatedEndereco)
                } else {
                    EnderecoClient.api.criar(candidatoId, updatedEndereco)
                }
                
                withContext(Dispatchers.Main) {
                    val updatedCandidato = currentCandidato.copy(endereco = result)
                    viewModel.setCandidato(updatedCandidato)
                    Toast.makeText(context, "Endereço salvo com sucesso!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro ao salvar endereço: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
