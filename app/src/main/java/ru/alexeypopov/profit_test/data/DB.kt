package ru.alexeypopov.profit_test.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alexeypopov.profit_test.utils.DB_NAME

/**
 * Класс собственно базы данных
 */
@Database(entities = [QRCode::class], version = 1)
abstract class DB: RoomDatabase(){
    abstract fun QRCodesDao(): QRCodeDAO

    //Делаем синглтон данной БД
    //Да, это можно сделать и в даггере, но для надежности лучше так
    companion object {
        @Volatile private var instance: DB? = null

        fun getInstance(context: Context): DB {
                return instance ?: synchronized(this) {
                    instance ?: buildDatabase(context).also { instance = it }
                }
        }

        /**
         * Создаём базу данных
         */
        private fun buildDatabase(context: Context): DB {
            return Room.databaseBuilder(context, DB::class.java, DB_NAME).build()
        }

    }
}