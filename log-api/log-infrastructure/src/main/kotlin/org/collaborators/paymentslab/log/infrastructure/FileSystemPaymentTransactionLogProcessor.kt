package org.collaborators.paymentslab.log.infrastructure

import com.fasterxml.jackson.databind.ObjectMapper
import org.collaborators.paymentslab.log.domain.TransactionLogProcessor
import org.collaborators.paymentslab.log.domain.PaymentResultEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FileSystemPaymentTransactionLogProcessor(
    private val objectMapper: ObjectMapper
): TransactionLogProcessor<PaymentResultEvent> {
    private val logger = LoggerFactory.getLogger("payment")
    @Value("\${event.record.dir}")
    private lateinit var recordDir: String

    private val prefix = "payment"

    override fun execute(event: PaymentResultEvent) {
        try {
            val root = Paths.get(recordDir)
            ensureDirectoryWritable(root)

            val paymentRecordLogName = constructFileName()
            val file = File(recordDir, paymentRecordLogName)
            val fw = FileWriter(file, true)
            val pw = PrintWriter(fw)
            pw.println(objectMapper.writeValueAsString(event))
            pw.flush()
        } catch (e: Exception) {
            println(e.message)
        }

    }

    private fun ensureDirectoryWritable(path: Path) {
        if (Files.notExists(path)) {
            Files.createDirectories(path)
        }
        if (!Files.isWritable(path)) {
            throw IOException("유효하지 않은 디렉토리 입니다. $path")
        }
    }

    private fun constructFileName(): String {
        val now = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return "$prefix-${now.format(formatter)}.log"
    }
}