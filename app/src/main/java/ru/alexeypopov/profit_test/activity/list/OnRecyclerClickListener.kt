package ru.alexeypopov.profit_test.activity.list

/**
 * Интерфейс, через который мы будем передавать соответствующий айтему объект датасета
 */
interface OnRecyclerClickListener<T> {

    /**
     * Коллбэк клика по айтему
     * @param item - объект из датасета
     */
    fun onItemClicked(item: T)
}