package com.example.curriculumapp.ui.candidato

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.curriculumapp.client.candidato.CandidatoClient
import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import com.example.curriculumapp.client.usuario.UsuarioClient
import kotlinx.coroutines.launch

class CurriculoViewModel : ViewModel() {

    private val _candidato = MutableLiveData<CandidatoDTO?>()
    val candidato: LiveData<CandidatoDTO?> = _candidato

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun loadCandidato() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Try to fetch the profile of the logged-in candidate
                try {
                    val profile = CandidatoClient.api.me()
                    _candidato.value = profile
                } catch (e: Exception) {
                    // If candidate profile doesn't exist yet, we can pre-fill with basic user data
                    val user = UsuarioClient.api.getMe()
                    _candidato.value = CandidatoDTO(
                        idUsuario = user.id, 
                        nome = user.nome + " " + user.sobrenome,
                        email = user.email, 
                        telefone = user.telefone
                    )
                }
            } catch (e: Exception) {
                _error.value = "Erro ao carregar dados: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun setCandidato(updated: CandidatoDTO) {
        _candidato.value = updated
    }
}
