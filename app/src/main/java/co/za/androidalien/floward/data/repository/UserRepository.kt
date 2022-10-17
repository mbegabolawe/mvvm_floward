package co.za.androidalien.floward.data.repository

import android.util.Log
import co.za.androidalien.floward.data.database.MyDatabase
import co.za.androidalien.floward.data.services.UserService
import co.za.androidalien.floward.data.utils.NetworkResult
import co.za.androidalien.floward.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val userService: UserService,
    private val database: MyDatabase) {


    suspend fun getUsers() : Flow<NetworkResult<List<User>>> = flow<NetworkResult<List<User>>> {
        emit(NetworkResult.Loading())

        val localData = database.userDao().getAllUsers()
        if (localData.isNotEmpty()) {
            emit(NetworkResult.Success(localData))
            Log.d("GET_USERS", "Local data: $localData")
        }

        with(userService.getUsers()) {
            if (isSuccessful) {
                body()?.let {
                    Log.d("GET_USERS", "result: $it")
                    database.userDao().insertUsers(it)
                    emit(NetworkResult.Success(it))
                }
            } else {
                emit(NetworkResult.Error("Failed to retrieve users"))
            }
        }
    }.catch {
        emit(NetworkResult.Error("Failed to retrieve users completely"))
    }
}