package ru.alexeypopov.profit_test.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность QR-кода
 */

@Entity(tableName = "qr_codes")
data class QRCode(

    /** ID кода в таблице */
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    /** Сообщение в QR-коде */
    val message: String = ""
)