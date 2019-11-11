package ru.alexeypopov.profit_test.di.modules

import dagger.Module
import dagger.Provides
import ru.alexeypopov.profit_test.activity.list.ListContract
import ru.alexeypopov.profit_test.activity.list.ListModelImpl
import javax.inject.Singleton

/**
 * Модуль, который будет предоставлять модели презентерам
 */
@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideListModel(): ListContract.ListModel {
        return ListModelImpl()
    }
}