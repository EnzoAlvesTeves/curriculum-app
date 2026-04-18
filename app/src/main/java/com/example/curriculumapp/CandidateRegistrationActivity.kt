package com.example.curriculumapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.curriculumapp.databinding.ActivityCandidateRegistrationBinding
import java.util.*

class CandidateRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCandidateRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCandidateRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupDatePickers()
        setupSaveButton()
    }

    private fun setupSpinners() {
        // Gender Spinner
        val genders = arrayOf("Selecione", "Masculino", "Feminino", "Outro", "Prefiro não dizer")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = genderAdapter

        // Skill Level Spinner
        val levels = arrayOf("Básico", "Intermediário", "Avançado")
        val levelAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, levels)
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSkillLevel.adapter = levelAdapter
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
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = String.format(Locale.getDefault(), "%02d / %02d / %d", selectedDay, selectedMonth + 1, selectedYear)
                editText.setText(date)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun setupSaveButton() {
        binding.btnSave.setOnClickListener {
            val name = binding.etFullName.text.toString()
            if (name.isNotEmpty()) {
                Toast.makeText(this, "Candidato $name salvo com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Por favor, preencha o nome.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}