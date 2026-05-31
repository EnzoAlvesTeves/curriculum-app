package com.example.curriculumapp.util

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val uiFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private val apiFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun showDatePicker(context: Context, editText: EditText) {
        val calendar = Calendar.getInstance()
        
        // Try to parse current date if exists
        val currentText = editText.text.toString()
        if (currentText.isNotEmpty()) {
            try {
                uiFormat.parse(currentText)?.let { calendar.time = it }
            } catch (e: Exception) {}
        }

        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)
                editText.setText(uiFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun formatToApi(uiDate: String?): String? {
        if (uiDate.isNullOrEmpty()) return null
        return try {
            val date = uiFormat.parse(uiDate)
            apiFormat.format(date!!)
        } catch (e: Exception) {
            uiDate // Return original if parsing fails
        }
    }

    fun formatToUi(apiDate: String?): String? {
        if (apiDate.isNullOrEmpty()) return null
        return try {
            // Check if it's yyyy-MM-dd or yyyy-MM
            val date = if (apiDate.length == 7) {
                SimpleDateFormat("yyyy-MM", Locale.getDefault()).parse(apiDate)
            } else {
                apiFormat.parse(apiDate)
            }
            uiFormat.format(date!!)
        } catch (e: Exception) {
            apiDate
        }
    }
}
