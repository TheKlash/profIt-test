package ru.alexeypopov.profit_test.data

import androidx.room.*

/**
 * DAO для QR-кода
 * Это внутренний DAO базы данных, т.е он не относится к репозиторию слоя бизнес-логики,
 * в котором мы будем хранить сами объекты.
 * В Room для описания запросов к самой БД используются имено DAO
 */
@Dao
interface QRCodeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(code: QRCode): Long

    @Delete
    fun delete(code: QRCode)

    @Query("SELECT * FROM qr_codes")
    fun getAll(): List<QRCode>

    @Query("SELECT MAX(id) FROM qr_codes")
    fun getBiggestId(): Long
}