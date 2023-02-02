package io.github.keddnyo.neopad.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.keddnyo.neopad.data.database.room.dao.NoteRoomDao
import io.github.keddnyo.neopad.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun getRoomDao(): NoteRoomDao

    companion object {
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context = context,
                    klass = AppRoomDatabase::class.java,
                    "notes_database"
                ).build()
                INSTANCE as AppRoomDatabase
            } else {
                INSTANCE as AppRoomDatabase
            }
        }
    }
}