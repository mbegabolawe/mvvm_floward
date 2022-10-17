package co.za.androidalien.floward.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.za.androidalien.floward.data.models.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(users: List<Post>)

    @Query("Select * from post where id like :id")
    suspend fun getPost(id: String) : Post

    @Query("Select * from post where userId like :userId")
    suspend fun getAllPosts(userId: Int) : List<Post>

    @Query("Delete from post")
    suspend fun clear()
}