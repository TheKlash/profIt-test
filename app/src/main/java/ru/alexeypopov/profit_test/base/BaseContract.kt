package ru.alexeypopov.profit_test.base

/**
 * Базовый контракт для всех экранов.
 * Для контрактов каждого конкретного экрана, интерфейсы View, Presenter и Model
 * будут реализованы под требования этих экранов, но сами эти интерфейсы будут реализованы уже
 * в BaseActivity
 */
interface BaseContract {

    /**
     * Слой представления
     */
    interface View {

        /**
         * Показывает прогрессбар
         * @param
         */
        fun showProgress()

        /**
         * Скрывает прогрессбар
         * @param
         */
        fun hideProgress()

        /**
         * Показывает тост.
         * @param message сообщение для тоста
         */
        fun toast(message: String)
    }

    /**
     * Слой презентеров
     */
    interface Presenter {


    }

    /**
     * Слой бизнес-логики
     */

    interface Model {

    }
}