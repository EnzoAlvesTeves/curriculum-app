package com.example.curriculumapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.curriculumapp.client.candidato.CandidatoClient
import com.example.curriculumapp.client.candidato.dto.*
import com.example.curriculumapp.client.usuario.dto.UsuarioDTO
import com.example.curriculumapp.databinding.ActivityCandidateRegistrationBinding
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.*

class CandidateRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCandidateRegistrationBinding
    private lateinit var sessionManager: SessionManager
    private var usuarioDTO: UsuarioDTO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCandidateRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        usuarioDTO = getUsuarioFromIntent() ?: sessionManager.getSession()

        setupSpinners()
        setupDatePickers()
        setupSaveButton()
    }

    private fun getUsuarioFromIntent(): UsuarioDTO? {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("usuarioDTO", UsuarioDTO::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("usuarioDTO") as? UsuarioDTO
        }
    }

    private fun setupSpinners() {
        val genders = arrayOf("Selecione", "Masculino", "Feminino", "Outro")
        binding.spinnerGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders)

        val levels = arrayOf("Básico", "Intermediário", "Avançado")
        binding.spinnerSkillLevel.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, levels)
    }

    private fun setupDatePickers() {
        binding.etBirthDate.setOnClickListener { showDatePicker(binding.etBirthDate) }
        binding.etExpStartDate.setOnClickListener { showDatePicker(binding.etExpStartDate) }
        binding.etExpEndDate.setOnClickListener { showDatePicker(binding.etExpEndDate) }
        binding.etEduStartDate.setOnClickListener { showDatePicker(binding.etEduStartDate) }
        binding.etEduEndDate.setOnClickListener { showDatePicker(binding.etEduEndDate) }
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, day ->
            val date = String.format(Locale.getDefault(), "%02d/%02d/%d", day, month + 1, year)
            editText.setText(date)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val candidatoDTO = CandidatoDTO(
                nome = binding.etFullName.text.toString(),
                email = usuarioDTO?.email ?: "",
                telefone = binding.etPhone.text.toString(),
                sexo = binding.spinnerGender.selectedItem.toString(),
                dataNascimento = binding.etBirthDate.text.toString(),
                resumoProfissional = "Candidato cadastrado via App",
                usuarioId = usuarioDTO?.id?.toInt(),
                endereco = EnderecoDTO(
                    cep = binding.etCep.text.toString(),
                    rua = binding.etStreet.text.toString(),
                    numero = binding.etNumber.text.toString(),
                    bairro = binding.etNeighborhood.text.toString(),
                    complemento = "",
                    cidade = "",
                    estado = ""
                ),
                experiencias = listOf(ExperienciaDTO(
                    cargo = binding.etJobRole.text.toString(),
                    empresa = binding.etCompany.text.toString(),
                    dataInicio = binding.etExpStartDate.text.toString(),
                    dataFim = binding.etExpEndDate.text.toString()
                )),
                educacoes = listOf(EducacaoDTO(
                    instituicao = binding.etInstitution.text.toString(),
                    curso = binding.etCourse.text.toString(),
                    grau = binding.etDegree.text.toString(),
                    dataInicio = binding.etEduStartDate.text.toString(),
                    dataFim = binding.etEduEndDate.text.toString()
                )),
                habilidades = listOf(HabilidadeDTO(
                    descricao = binding.etSkillDescription.text.toString(),
                    nivel = binding.spinnerSkillLevel.selectedItem.toString(),
                    especialidade = binding.etSpecialty.text.toString()
                ))
            )

            lifecycleScope.launch {
                try {
                    CandidatoClient.api.salvar(candidatoDTO)
                    Toast.makeText(this@CandidateRegistrationActivity, "Perfil salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Erro ao salvar: ${e.message}")
                    Toast.makeText(this@CandidateRegistrationActivity, "Erro ao salvar perfil", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}