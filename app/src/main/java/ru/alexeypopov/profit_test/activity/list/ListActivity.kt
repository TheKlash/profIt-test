package ru.alexeypopov.profit_test.activity.list

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*
import ru.alexeypopov.profit_test.R
import ru.alexeypopov.profit_test.activity.camera.CameraActivity
import ru.alexeypopov.profit_test.base.BaseActivity
import ru.alexeypopov.profit_test.data.QRCode
import ru.alexeypopov.profit_test.utils.CAMERA_REQUEST_CODE
import ru.alexeypopov.profit_test.utils.CAMERA_RESULT_OK
import ru.alexeypopov.profit_test.utils.DatabaseObject
import javax.inject.Inject

/**
 * Активити для списка QR-кодов
 */
class ListActivity : BaseActivity(),  ListContract.ListView {

    @Inject
    lateinit var presenter: ListContract.ListPresenter
    lateinit var recyclerAdapter: QRRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(activity_list_toolbar)

        initActionBar()

        initScan()
        initDatabaseContext()
        inject()
        initRecycler()
    }

    override fun onStart() {
        super.onStart()
        presenter.getQRCodes()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            CAMERA_RESULT_OK -> {
                data?.let {
                    val message = data.getStringExtra("message")
                    presenter.addQRCode(message)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initActionBar() {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = getString(R.string.scan_list)
    }

    private fun initScan() {
        activity_list_scan_button.setOnClickListener {
            val intent = Intent(this@ListActivity, CameraActivity::class.java)
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        }
    }

    private fun inject() {
        val component = DatabaseObject.getComponent()
        component.inject(this)
        presenter.setListView(this)
    }

    private fun initRecycler() {
        activity_list_recycler.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = QRRecyclerAdapter(mutableListOf(), RecyclerClickListener()) //Создаём адаптер, пока с пустым списком
        activity_list_recycler.adapter = recyclerAdapter

        presenter.getQRCodes()
    }

    /**
     * Передаём контекст приложения в DatabaseObject
     * Поскольку это главная активити нашего приложения
     * и она всегда запускается первой, то можно передавать контекст
     * при её создании
     */
    private fun initDatabaseContext() {
        DatabaseObject.context = application.applicationContext
    }

    override fun updateList(codes: List<QRCode>) {
        recyclerAdapter.updateDataset(codes)
    }

    /**
     * Листенер, который будет слушать нажатия на элементы списка
     * Оформил как внутренний класс, потому что, с одной стороны, делать листенером всю
     * активити (она же context) и передавать её в адаптер как-то некрасиво,
     * а с другой стороны нужен доступ к методам и полям самой активити
     */
    inner class RecyclerClickListener: OnRecyclerClickListener<QRCode> {
        override fun onItemClicked(item: QRCode) {
            presenter.removeQRCode(item)
        }
    }
}
