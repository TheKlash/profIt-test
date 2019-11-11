package ru.alexeypopov.profit_test.di.modules

import dagger.Module
import dagger.Provides
import ru.alexeypopov.profit_test.data.DB
import ru.alexeypopov.profit_test.data.QRCodeRepository

@Module
class ModelModule(val db: DB) {

    @Provides
    fun provideQRCodeRepository(): QRCodeRepository {
        return QRCodeRepository.getInstance(db.QRCodesDao())
    }
}