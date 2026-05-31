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
import com.example.curriculumapp.client.usuario.dto.AlterarSenhaRequest
import com.example.curriculumapp.databinding.FragmentChangePasswordBinding
import kotlinx.coroutines.launch

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUpdatePassword.setOnClickListener { updatePassword() }
    }

    private fun updatePassword() {
        val current = binding.etCurrentPassword.text.toString()
        val new = binding.etNewPassword.text.toString()

        if (current.isEmpty() || new.isEmpty()) {
            Toast.makeText(context, "Preencha as senhas", Toast.LENGTH_SHORT).show()
            return
        }

        val request = AlterarSenhaRequest(current, new)
        lifecycleScope.launch {
            try {
                UsuarioClient.api.alterarSenha(request)
                Toast.makeText(context, R.string.senha_alterada_sucesso, Toast.LENGTH_SHORT).show()
                binding.etCurrentPassword.text?.clear()
                binding.etNewPassword.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao alterar senha", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
