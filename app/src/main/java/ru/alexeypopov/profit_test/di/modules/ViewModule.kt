package ru.alexeypopov.profit_test.di.modules

import dagger.Module
import dagger.Provides
import ru.alexeypopov.profit_test.activity.list.ListContract
import ru.alexeypopov.profit_test.activity.list.ListPresenterImpl

/**
 * Модуль, который будет предоставлять презенторы компонентам слоя представления
 */
@Module
class ViewModule {

    @Provides
    fun provideListPresenter(): ListContract.ListPresenter {
        return ListPresenterImpl()
    }
}