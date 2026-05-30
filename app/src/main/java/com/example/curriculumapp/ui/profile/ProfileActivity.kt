package com.example.curriculumapp.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.R
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.client.usuario.dto.AlterarSenhaRequest
import com.example.curriculumapp.client.usuario.dto.UpdateUsuarioRequest
import com.example.curriculumapp.databinding.ActivityProfileBinding
import com.example.curriculumapp.ui.base.BaseActivity
import kotlinx.coroutines.launch

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private var userId: Long? = null
    private var userType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // For Profile, we use a standard toolbar instead of the custom header
        binding.toolbar.setNavigationOnClickListener { finish() }

        loadUserData()

        binding.btnUpdateProfile.setOnClickListener {
            updateProfile()
        }

        binding.btnUpdatePassword.setOnClickListener {
            updatePassword()
        }

        binding.btnDeleteAccount.setOnClickListener {
            showDeleteConfirmation()
        }
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
                Toast.makeText(this@ProfileActivity, "Erro ao carregar perfil", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@ProfileActivity, R.string.perfil_atualizado, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updatePassword() {
        val current = binding.etCurrentPassword.text.toString()
        val new = binding.etNewPassword.text.toString()

        if (current.isEmpty() || new.isEmpty()) {
            Toast.makeText(this, "Preencha as senhas", Toast.LENGTH_SHORT).show()
            return
        }

        val request = AlterarSenhaRequest(current, new)

        lifecycleScope.launch {
            try {
                UsuarioClient.api.alterarSenha(request)
                Toast.makeText(this@ProfileActivity, R.string.senha_alterada_sucesso, Toast.LENGTH_SHORT).show()
                binding.etCurrentPassword.text?.clear()
                binding.etNewPassword.text?.clear()
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Erro ao alterar senha", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle(R.string.deletar_conta)
            .setMessage(R.string.confirmar_exclusao)
            .setPositiveButton("Deletar") { _, _ -> deleteAccount() }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteAccount() {
        val id = userId ?: return
        lifecycleScope.launch {
            try {
                UsuarioClient.api.deletar(id)
                Toast.makeText(this@ProfileActivity, R.string.usuario_deletado, Toast.LENGTH_SHORT).show()
                logout() // Call the base logout
            } catch (e: Exception) {
                Toast.makeText(this@ProfileActivity, "Erro ao deletar conta", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
