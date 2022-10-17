package co.za.androidalien.floward.data.repository

import android.util.Log
import co.za.androidalien.floward.data.database.MyDatabase
import co.za.androidalien.floward.data.services.PostService
import co.za.androidalien.floward.data.utils.NetworkResult
import co.za.androidalien.floward.data.models.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class PostRepository(
    private val postService: PostService,
    private val database: MyDatabase
) {

    suspend fun getPosts() : Flow<NetworkResult<List<Post>>> = flow<NetworkResult<List<Post>>> {
        emit(NetworkResult.Loading())

        //No need to retrieve from db
        with(postService.getPosts()) {
            if (isSuccessful) {
                body()?.let {
                    database.postDao().insertPosts(it)
                    emit(NetworkResult.Success(it))
                }
            } else {
                emit(NetworkResult.Error("Failed to retrieve post"))
            }
        }
    }.catch {
        emit(NetworkResult.Error("Failed to retrieve post completely"))
    }

    suspend fun getPostsByUser(userId: Int) : Flow<NetworkResult<List<Post>>> = flow {
        emit(NetworkResult.Loading())

        val localData = database.postDao().getAllPosts(userId)
        if (localData.isNotEmpty()) {
            emit(NetworkResult.Success(localData))
            Log.d("GET_POSTS", "Local data: $localData")
        } else {
            emit(NetworkResult.Error("No posts found for user"))
        }
    }

    suspend fun clearData() {
        database.postDao().clear()
    }
}