package com.example.curriculumapp.ui.profile.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.CurriculumApplication
import com.example.curriculumapp.R
import com.example.curriculumapp.client.usuario.UsuarioClient
import com.example.curriculumapp.databinding.FragmentDeleteAccountBinding
import com.example.curriculumapp.ui.login.LoginActivity
import kotlinx.coroutines.launch

class DeleteAccountFragment : Fragment() {

    private var _binding: FragmentDeleteAccountBinding? = null
    private val binding get() = _binding!!
    private var userId: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDeleteAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUserId()
        binding.btnDeleteAccount.setOnClickListener { showDeleteConfirmation() }
    }

    private fun loadUserId() {
        lifecycleScope.launch {
            try {
                val user = UsuarioClient.api.getMe()
                userId = user.id
            } catch (e: Exception) {
                // Ignore
            }
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.deletar_conta)
            .setMessage(R.string.confirmar_exclusao)
            .setPositiveButton(R.string.deletar) { _, _ -> deleteAccount() }
            .setNegativeButton(R.string.cancelar, null)
            .show()
    }

    private fun deleteAccount() {
        val id = userId ?: return
        binding.loading.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                UsuarioClient.api.deletar(id)
                Toast.makeText(context, R.string.usuario_deletado, Toast.LENGTH_SHORT).show()
                logout()
            } catch (e: Exception) {
                Toast.makeText(context, "Erro ao deletar conta", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }

    private fun logout() {
        CurriculumApplication.instance.tokenManager.clearTokens()
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
