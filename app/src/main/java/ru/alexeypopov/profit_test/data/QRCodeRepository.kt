package ru.alexeypopov.profit_test.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

/**
 * Репозиторий для наших QR-кодов
 */
class QRCodeRepository private constructor(private val qrCodeDao: QRCodeDAO){
    //Методы репозитория используют корутины для доступа к БД, чтобы не получать к ней доступ из основного потока

    /**
     * Метод получения всех имеющихся QR-кодов
     * @param
     * @return список QR-кодов
     */
    suspend fun getCodes():List<QRCode> {
        val list = GlobalScope.async (Dispatchers.IO) { qrCodeDao.getAll() }.await()
        return list
    }

    /**
     * Метод удаления кода из БД
     * @param qrCode код, который надо удалить
     */
    suspend fun removeCode(qrCode: QRCode) = GlobalScope.async (Dispatchers.IO) { qrCodeDao.delete(qrCode) }.await()

    /**
     * Метод создания нового QR-кода
     * При передачи сообщения из кода, ему присваивается индекс, равный текущему размеру таблицы
     * @param message сообщение из QR-кода
     */
    suspend fun addCode(message: String) {
        val code = QRCode(0, message)
        GlobalScope.async (Dispatchers.IO) { qrCodeDao.insert(code) }.await()
    }

    //Делаем репозиторий синглтоном
    companion object {

        @Volatile private var instance: QRCodeRepository? = null

        fun getInstance(qrCodeDao: QRCodeDAO) =
            instance ?: synchronized(this) {
                instance ?: QRCodeRepository(qrCodeDao).also { instance = it }
            }
    }

}