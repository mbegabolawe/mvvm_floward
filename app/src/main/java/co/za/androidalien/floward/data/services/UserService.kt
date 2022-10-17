package co.za.androidalien.floward.data.services

import co.za.androidalien.floward.data.models.User
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("users")
    suspend fun getUsers() : Response<List<User>>
}