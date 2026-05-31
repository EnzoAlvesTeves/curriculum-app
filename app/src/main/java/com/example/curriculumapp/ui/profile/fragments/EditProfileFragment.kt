package com.example.curriculumapp.ui.profile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.R
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.client.usuario.dto.UpdateUsuarioRequest
import com.example.curriculumapp.databinding.FragmentEditProfileBinding
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var userId: Long? = null
    private var userType: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserData()
        binding.btnUpdateProfile.setOnClickListener { updateProfile() }
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            try {
                val user = UsuarioClient.api.getMe()
                userId = user.id
                userType = user.tipo
                binding.etName.setText(user.nome)
                binding.etLastName.setText(user.sobrenome)
                binding.etPhone.setText(user.telefone)
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao carregar dados", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateProfile() {
        val id = userId ?: return
        val tipo = userType ?: return
        val request = UpdateUsuarioRequest(
            nome = binding.etName.text.toString(),
            sobrenome = binding.etLastName.text.toString(),
            telefone = binding.etPhone.text.toString(),
            tipo = tipo
        )
        lifecycleScope.launch {
            try {
                UsuarioClient.api.alterar(id, request)
                Toast.makeText(context, R.string.perfil_atualizado, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
