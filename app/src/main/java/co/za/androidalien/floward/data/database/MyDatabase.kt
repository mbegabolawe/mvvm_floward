package co.za.androidalien.floward.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase
import co.za.androidalien.floward.data.dao.PostDao
import co.za.androidalien.floward.data.dao.UserDao
import co.za.androidalien.floward.data.models.Post
import co.za.androidalien.floward.data.models.User
import co.za.androidalien.floward.utils.Constants.Companion.DB_NAME

@Database(
    entities = [
        User::class,
        Post::class
    ],
    version = 1
)
abstract class MyDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also {
                    INSTANCE = it
                }
            }
        }

        private fun buildDatabase(context: Context) : MyDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}