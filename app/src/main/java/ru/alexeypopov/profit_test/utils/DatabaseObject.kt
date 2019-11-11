package ru.alexeypopov.profit_test.utils

import android.content.Context
import ru.alexeypopov.profit_test.data.DB
import ru.alexeypopov.profit_test.di.components.AppComponent
import ru.alexeypopov.profit_test.di.components.DaggerAppComponent
import ru.alexeypopov.profit_test.di.modules.ModelModule
import ru.alexeypopov.profit_test.di.modules.PresenterModule
import ru.alexeypopov.profit_test.di.modules.ViewModule

/**
 * Маленький костыль: мы создаём синглтон, где уже будет
 * экземпляр БД.
 * Вся проблема в том, что БД должна инициализироваться с помощью
 * Application Context, но вопрос: как его передать в слой моделей?
 * Данный синглтон будет создавать экземпляр при запуске приложения,
 * но получить его можно будет вообще в любом месте приложения.
 *
 * (Это не является утечкой памяти, потому что ссылка на контекст
 * приложения актуальна, пока само приложение работает)
 */

object DatabaseObject {

    var context: Context? = null
    lateinit var appComponent: AppComponent

    fun getDatabase(): DB {
        return DB.getInstance(context!!)
    }

    fun getComponent(): AppComponent {
        if (!::appComponent.isInitialized) {
            appComponent = DaggerAppComponent.builder()
                .modelModule(ModelModule(getDatabase()))
                .presenterModule(PresenterModule())
                .viewModule(ViewModule())
                .build()
        }
        return appComponent
    }

}