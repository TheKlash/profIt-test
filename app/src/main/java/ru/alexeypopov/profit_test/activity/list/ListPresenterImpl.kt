package ru.alexeypopov.profit_test.activity.list

import ru.alexeypopov.profit_test.data.QRCode
import ru.alexeypopov.profit_test.di.components.DaggerAppComponent
import ru.alexeypopov.profit_test.di.modules.PresenterModule
import ru.alexeypopov.profit_test.utils.DatabaseObject
import javax.inject.Inject

class ListPresenterImpl: ListContract.ListPresenter {

    @Inject
    lateinit var model: ListContract.ListModel
    var view: ListContract.ListView? = null

    init {
        val component = DatabaseObject.getComponent()
        component.inject(this)
    }

    override fun setListView(view: ListContract.ListView) {
        this.view = view
    }

    override fun getQRCodes() {
        val list = model.getQRCodesFromRepository()
        updateCodesList(list)
    }

    override fun removeQRCode(code: QRCode) {
        val list = model.removeQRCodeFromRepository(code)
        updateCodesList(list)
    }

    override fun addQRCode(message: String) {
        val list = model.addQRCodeToRepository(message)
        updateCodesList(list)
    }

    private fun updateCodesList(list: List<QRCode>) {
        view?.updateList(list)
    }
}