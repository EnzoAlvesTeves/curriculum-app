package com.example.curriculumapp.util.pdf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import com.example.curriculumapp.client.candidato.dto.CandidatoDTO
import java.io.FileOutputStream
import java.io.IOException

object PdfGenerator {

    fun printCurriculumPdf(context: Context, candidato: CandidatoDTO) {
        val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
        val jobName = "Curriculo_${candidato.nome?.replace(" ", "_")}"

        printManager.print(jobName, object : PrintDocumentAdapter() {
            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes?,
                cancellationSignal: CancellationSignal?,
                callback: LayoutResultCallback?,
                extras: Bundle?
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback?.onLayoutCancelled()
                    return
                }

                val pdi = PrintDocumentInfo.Builder(jobName)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(1) // Por enquanto simplificado para 1 página
                    .build()

                callback?.onLayoutFinished(pdi, true)
            }

            override fun onWrite(
                pages: Array<out PageRange>?,
                destination: ParcelFileDescriptor?,
                cancellationSignal: CancellationSignal?,
                callback: WriteResultCallback?
            ) {
                val pdfDocument = PdfDocument()
                val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
                val page = pdfDocument.startPage(pageInfo)
                
                // Desenha o conteúdo no canvas do PDF
                drawCurriculumContent(page.canvas, candidato)
                
                pdfDocument.finishPage(page)

                try {
                    pdfDocument.writeTo(FileOutputStream(destination?.fileDescriptor))
                } catch (e: IOException) {
                    callback?.onWriteFailed(e.toString())
                    return
                } finally {
                    pdfDocument.close()
                }

                callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
            }
        }, null)
    }

    private fun drawCurriculumContent(canvas: Canvas, candidato: CandidatoDTO) {
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
            y = drawMultilineText(canvas, candidato.resumoProfissional!!, x, y, 500f)
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
                    y = drawMultilineText(canvas, exp.resumo!!, x + 10f, y, 480f)
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
