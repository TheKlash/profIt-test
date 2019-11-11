package ru.alexeypopov.profit_test.activity.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.alexeypopov.profit_test.R
import ru.alexeypopov.profit_test.data.QRCode

/**
 * Адаптер для ресайклера с кодами. Стандартная реализация
 */
class QRRecyclerAdapter(val dataset: MutableList<QRCode>, val listener: OnRecyclerClickListener<QRCode>)
    : RecyclerView.Adapter<QRRecyclerAdapter.QRViewHolder>() {

    inner class QRViewHolder : RecyclerView.ViewHolder {
        val text: TextView

        constructor(view: View): super(view) {
            text = itemView.findViewById(R.id.qr_recycler_label)
            //Ставим литсенер клика по элементу списка
            itemView.setOnClickListener {
                //При нажатии на элемент списка - возвращаем соответствующий QR-код
                listener.onItemClicked(dataset[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QRViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.qr_recycler_item, parent, false)
        return QRViewHolder(view)
    }

    override fun onBindViewHolder(holder: QRViewHolder, position: Int) {
        holder.text.text = dataset[position].message
    }

    override fun getItemCount() = dataset.size

    /**
     * Метод для обновления датасета
     */
    fun updateDataset(newList: List<QRCode>) {
        dataset.clear()
        dataset.addAll(newList)
        notifyDataSetChanged()
    }
}