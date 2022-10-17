package co.za.androidalien.floward.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.za.androidalien.floward.data.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Query("Select * from user")
    suspend fun getAllUsers() : List<User>

    @Query("Select * from user where userId like :userId")
    suspend fun getUser(userId: Int) : User

    @Query("Delete from user")
    suspend fun clear()
}