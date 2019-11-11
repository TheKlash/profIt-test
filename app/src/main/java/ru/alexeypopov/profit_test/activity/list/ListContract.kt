package ru.alexeypopov.profit_test.activity.list

import ru.alexeypopov.profit_test.base.BaseContract
import ru.alexeypopov.profit_test.data.QRCode

interface ListContract {

    /**
     * Слой представления для ListActivity
     */
    interface ListView: BaseContract.View {
        /**
         * Возвращает список всех QR-кодов
         * @param codes QR-коды
         */
        fun updateList(codes: List<QRCode>)
    }

    /**
     * Слой презентеров для ListActivity
     */
    interface ListPresenter: BaseContract.Presenter {

        /**
         * Метод для запроса всех QR-кодов
         * @param
         */
        fun getQRCodes()

        /**
         * Метод для удаления QR-кода
         * @param code - QR-код
         */
        fun removeQRCode(code: QRCode)

        /**
         * Метод для добавления QR-кода
         * @param message - сообщение в QR-коде
         */
        fun addQRCode(message: String)


        /**
         * Метод, в который мы передаём сслку на нашу View
         * Честно говоря, так и не придумал параметрический метод
         * для BaseContract
         * @param view ListView
         */
        fun setListView(view: ListContract.ListView)
    }

    /**
     * Слой бизнес-логики для ListActivity
     */
    interface ListModel: BaseContract.Model {
        /**
         * Возвращает репозиторий с QR-кодами
         * @param
         * @return список QR-кодов
         */
        fun getQRCodesFromRepository(): List<QRCode>


        /**
         * Удаляет QR-код из репозитория
         * @param code - QR-код
         * @return обновленный репозиторий
         */
        fun removeQRCodeFromRepository(code: QRCode): List<QRCode>

        /**
         * Добавляет QR-код в репозиторий
         * @param message - сообщение из QR-кода
         */
        fun addQRCodeToRepository(message: String): List<QRCode>
    }
}