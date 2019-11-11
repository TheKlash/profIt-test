package ru.alexeypopov.profit_test.activity.list

import kotlinx.coroutines.runBlocking
import ru.alexeypopov.profit_test.data.QRCode
import ru.alexeypopov.profit_test.data.QRCodeRepository
import ru.alexeypopov.profit_test.di.components.DaggerAppComponent
import ru.alexeypopov.profit_test.di.modules.ModelModule
import ru.alexeypopov.profit_test.utils.DatabaseObject
import javax.inject.Inject

/**
 * Имплементация бизнес-логики списка qr-кодов
 */
class ListModelImpl: ListContract.ListModel {

    @Inject
    lateinit var qrCodeRepository: QRCodeRepository

    init {
        val component = DatabaseObject.getComponent()
        component.inject(this)

    }

    override fun getQRCodesFromRepository(): List<QRCode> = runBlocking{
        val result = qrCodeRepository.getCodes()
        return@runBlocking result
    }

    override fun removeQRCodeFromRepository(code: QRCode): List<QRCode> = runBlocking{
        qrCodeRepository.removeCode(code)
        val result = qrCodeRepository.getCodes()
        return@runBlocking result
    }

    override fun addQRCodeToRepository(message: String): List<QRCode> = runBlocking{
        qrCodeRepository.addCode(message)
        val result = qrCodeRepository.getCodes()
        return@runBlocking result
    }
}