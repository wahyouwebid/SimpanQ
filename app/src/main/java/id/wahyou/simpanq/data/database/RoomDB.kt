package id.wahyou.simpanq.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.wahyou.simpanq.data.database.dao.LinkDao
import id.wahyou.simpanq.data.database.dao.PlanDao
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.data.model.Planning

@Database(
    entities = [
        Link::class,
        Planning::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RoomDB : RoomDatabase() {

    abstract fun link() : LinkDao
    abstract fun plan() : PlanDao

    companion object {

        @Volatile private var instance : RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "SimpanQ.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    }
}