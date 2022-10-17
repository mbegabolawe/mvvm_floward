package co.za.androidalien.floward.data.services

import co.za.androidalien.floward.data.models.Post
import co.za.androidalien.floward.data.models.User
import retrofit2.Response
import retrofit2.http.GET

interface PostService {

    @GET("posts")
    suspend fun getPosts() : Response<List<Post>>
}