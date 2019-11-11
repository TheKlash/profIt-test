package ru.alexeypopov.profit_test.di.components

import dagger.Component
import ru.alexeypopov.profit_test.activity.list.ListActivity
import ru.alexeypopov.profit_test.activity.list.ListModelImpl
import ru.alexeypopov.profit_test.activity.list.ListPresenterImpl
import ru.alexeypopov.profit_test.di.modules.ModelModule
import ru.alexeypopov.profit_test.di.modules.PresenterModule
import ru.alexeypopov.profit_test.di.modules.ViewModule
import javax.inject.Singleton

/**
 * Компонента даггера, которая будет инжектить презентеры в активити
 * (простите за мой французский)
 */
@Singleton
@Component(modules = [ViewModule::class, PresenterModule::class, ModelModule::class])
interface AppComponent {

    /**
     * Инжектим в ListActivity, в его презентер и модель
     */
    fun inject(listActivity: ListActivity)
    fun inject(listPresenter: ListPresenterImpl)
    fun inject(listModel: ListModelImpl)
}