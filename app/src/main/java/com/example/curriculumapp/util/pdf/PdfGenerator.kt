package com.example.curriculumapp.util.pdf

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object PdfGenerator {

    fun generateCurriculumPdf(context: Context, candidato: CandidatoDTO) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 Size
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        val paint = Paint()
        val titlePaint = Paint()

        var y = 50f
        val x = 50f

        // Header - Name
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titlePaint.textSize = 24f
        titlePaint.color = Color.BLACK
        canvas.drawText(candidato.nome ?: "Nome não informado", x, y, titlePaint)
        y += 30f

        // Contact Info
        paint.textSize = 12f
        paint.color = Color.DKGRAY
        canvas.drawText("${candidato.email} | ${candidato.telefone ?: ""}", x, y, paint)
        y += 20f
        
        candidato.endereco?.let { e ->
            canvas.drawText("${e.rua}, ${e.numero} - ${e.bairro}, ${e.cidade}/${e.estado}", x, y, paint)
            y += 40f
        } ?: run { y += 20f }

        // Professional Summary
        if (!candidato.resumoProfissional.isNullOrEmpty()) {
            drawSectionTitle(canvas, "RESUMO PROFISSIONAL", x, y)
            y += 20f
            y = drawMultilineText(canvas, candidato.resumoProfissional, x, y, 500f)
            y += 30f
        }

        // Education
        if (candidato.educacoes.isNotEmpty()) {
            drawSectionTitle(canvas, "FORMAÇÃO ACADÊMICA", x, y)
            y += 20f
            for (edu in candidato.educacoes) {
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                canvas.drawText(edu.curso ?: "", x, y, paint)
                y += 15f
                paint.typeface = Typeface.DEFAULT
                canvas.drawText("${edu.instituicao} (${edu.dataInicio} - ${edu.dataFim})", x, y, paint)
                y += 25f
            }
            y += 10f
        }

        // Experience
        if (candidato.experiencias.isNotEmpty()) {
            drawSectionTitle(canvas, "EXPERIÊNCIA PROFISSIONAL", x, y)
            y += 20f
            for (exp in candidato.experiencias) {
                paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                canvas.drawText(exp.cargo ?: "", x, y, paint)
                y += 15f
                paint.typeface = Typeface.DEFAULT
                canvas.drawText("${exp.empresa} (${exp.dataInicio} - ${exp.dataFim})", x, y, paint)
                y += 15f
                if (!exp.resumo.isNullOrEmpty()) {
                    y = drawMultilineText(canvas, exp.resumo, x + 10f, y, 480f)
                }
                y += 20f
            }
        }

        // Skills
        if (candidato.habilidades.isNotEmpty()) {
            drawSectionTitle(canvas, "HABILIDADES", x, y)
            y += 20f
            val skills = candidato.habilidades.joinToString(", ") { "${it.descricao} (${it.nivel})" }
            drawMultilineText(canvas, skills, x, y, 500f)
        }

        pdfDocument.finishPage(page)

        val fileName = "Curriculo_${candidato.nome?.replace(" ", "_")}.pdf"
        saveToDownloads(context, pdfDocument, fileName)
    }

    private fun saveToDownloads(context: Context, pdfDocument: PdfDocument, fileName: String) {
        try {
            val outputStream: OutputStream?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = uri?.let { resolver.openOutputStream(it) }
            } else {
                val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloadsDir, fileName)
                outputStream = FileOutputStream(file)
            }

            outputStream?.use {
                pdfDocument.writeTo(it)
                Toast.makeText(context, "PDF salvo na pasta Downloads", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Erro ao salvar PDF: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            pdfDocument.close()
        }
    }

    private fun drawSectionTitle(canvas: Canvas, title: String, x: Float, y: Float) {
        val paint = Paint()
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        paint.textSize = 14f
        paint.color = Color.parseColor("#FF7000") // Primary Orange
        canvas.drawText(title, x, y, paint)
        canvas.drawLine(x, y + 5f, x + 500f, y + 5f, paint)
    }

    private fun drawMultilineText(canvas: Canvas, text: String, x: Float, y: Float, maxWidth: Float): Float {
        val paint = Paint()
        paint.textSize = 12f
        var currentY = y
        val words = text.split(" ")
        var line = ""
        for (word in words) {
            if (paint.measureText("$line $word") < maxWidth) {
                line += if (line.isEmpty()) word else " $word"
            } else {
                canvas.drawText(line, x, currentY, paint)
                line = word
                currentY += 15f
            }
        }
        canvas.drawText(line, x, currentY, paint)
        return currentY + 15f
    }
}
