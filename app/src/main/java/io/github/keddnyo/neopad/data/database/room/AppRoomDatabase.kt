package io.github.keddnyo.neopad.data.database.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.keddnyo.neopad.data.database.room.dao.NoteRoomDao

abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun getRoomDao(): NoteRoomDao

    companion object {
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context = context,
                    klass = AppRoomDatabase::class.java,
                    name = "notes_database"
                ).build()
            }

            return INSTANCE as AppRoomDatabase
        }
    }
}