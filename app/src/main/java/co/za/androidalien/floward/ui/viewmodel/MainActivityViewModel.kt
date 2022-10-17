package co.za.androidalien.floward.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.za.androidalien.floward.data.utils.NetworkResult
import co.za.androidalien.floward.data.models.Post
import co.za.androidalien.floward.data.models.User
import co.za.androidalien.floward.data.repository.PostRepository
import co.za.androidalien.floward.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository
) : ViewModel() {

    var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
    get() = _users


    var _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>>
    get() = _posts

    var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    var selectedUser: User? = null


    fun getUsers() = viewModelScope.launch {
        userRepository.getUsers().collect {
            when (it) {
                is NetworkResult.Error -> {
                    _loading.value = false
                }
                is NetworkResult.Loading -> {
                    _loading.value = true
                }
                is NetworkResult.Success -> {
                    _loading.value = false
                    it.data?.forEach { user ->
                        postRepository.getPostsByUser(user.userId).collect{
                            user.postCount = it.data?.size ?: 0
                        }
                    }
                    it.data?.let {
                        _users.value = it
                    }
                }
            }
        }
    }

    fun getPosts() = viewModelScope.launch {
        postRepository.getPosts().collect {
            when (it) {
                is NetworkResult.Error -> {
                    Log.d("GET_POSTS", "error: ${it.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d("GET_POSTS", "is Loading")
                }
                is NetworkResult.Success -> {
                    Log.d("GET_POSTS", "success: ${it.data}")
                }
            }
        }
    }

    fun getPostsByUser() =  viewModelScope.launch {
        selectedUser?.let {
            postRepository.getPostsByUser(it.userId).collect {
                when (it) {
                    is NetworkResult.Error -> {
                        Log.d("GET_POSTS_BY_USER", "error: ${it.message}")
                    }
                    is NetworkResult.Loading -> {
                        Log.d("GET_POSTS_BY_USER", "is Loading")
                    }
                    is NetworkResult.Success -> {
                        Log.d("GET_POSTS_BY_USER", "success, count = ${it.data?.size}: ${it.data}")
                        it.data?.let { list ->
                            _posts.value = list
                        }
                    }
                }
            }
        }
    }

    fun initializeData() = viewModelScope.launch {
        posts.value?.let {
            if (it.isNotEmpty()) {
                postRepository.clearData()
            }
        }
        selectedUser = null
    }
}